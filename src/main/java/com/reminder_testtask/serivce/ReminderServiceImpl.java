package com.reminder_testtask.serivce;

import com.reminder_testtask.dto.ReminderDto;
import com.reminder_testtask.entity.Reminder;
import com.reminder_testtask.entity.User;
import com.reminder_testtask.exception.NoAccessException;
import com.reminder_testtask.exception.ReminderNotFoundException;
import com.reminder_testtask.exception.UserNotFoundException;
import com.reminder_testtask.repository.ReminderRepository;
import com.reminder_testtask.utils.ReminderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserService userService;
    private final ReminderMapper mapper;

    public List<Reminder> listReminders() {
        return reminderRepository.findAll();
    }

    public void saveReminder(Reminder reminder, String user_id) throws UserNotFoundException {
        User user = userService.findById(user_id);
        reminder.setUser(user);
        reminderRepository.save(reminder);
    }

    public List<ReminderDto> getReminders() throws ReminderNotFoundException {
        if (listReminders().isEmpty()) {
            throw new ReminderNotFoundException("Напоминания не найдены!");
        }
        return mapper.remindersToListDto(listReminders());
    }

    public Page<ReminderDto> getRemindersWithPage(int total, int current) throws ReminderNotFoundException {
        if (listReminders().isEmpty()) {
            throw new ReminderNotFoundException("Напоминания не найдены!");
        }
        Pageable pageable = PageRequest.of(current, listReminders().size()/total);
        return mapper.reminderToPageDto(reminderRepository.findAll(pageable));
    }

    public void deleteReminderById(Long id) throws ReminderNotFoundException {
        reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Напоминание с ID:" + id + " не найден!"));
        reminderRepository.deleteById(id);
    }

    public ReminderDto findReminderByTitle(String title) throws ReminderNotFoundException {
        return mapper.reminderToDto( reminderRepository.findByTitle(title)
                .orElseThrow(() -> new ReminderNotFoundException("Напоминание с TITLE:" + title + " не найден!")));
    }

    public ReminderDto findReminderByDescription(String description) throws ReminderNotFoundException {
        return mapper.reminderToDto(reminderRepository.findByDescription(description)
                .orElseThrow(() ->
                        new ReminderNotFoundException("Напоминание с DESCRIPTION:" + description + " не найден!")));
    }

    public ReminderDto findReminderByDate(LocalDate date) throws ReminderNotFoundException {
        return mapper.reminderToDto(reminderRepository.findByRemind(date)
                .orElseThrow(() -> new ReminderNotFoundException("Напоминание с DATE:" + date + " не найден!")));
    }

    public ReminderDto findReminderById(Long id) throws ReminderNotFoundException {
        return mapper.reminderToDto(reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Напоминание с ID:" + id + " не найден!")));
    }

    public List<ReminderDto> sortReminder(String sortingBy) throws ReminderNotFoundException {
        if (listReminders().size() == 0) {
            throw new ReminderNotFoundException("Напоминания не найдены!");
        }
        return switch (sortingBy) {
            case ("title") -> mapper.remindersToListDto(
                    reminderRepository.findAll(Sort.by(Sort.Direction.ASC, "title")));
            case ("date") -> mapper.remindersToListDto(
                    reminderRepository.findAll(Sort.by(Sort.Direction.ASC, "remind")));
            default -> mapper.remindersToListDto(listReminders());
        };

    }

    public List<ReminderDto> filterByDate(LocalDate date) throws ReminderNotFoundException {
        if (listReminders().isEmpty()) {
            throw new ReminderNotFoundException("Напоминания не найдены!");
        }
        return mapper.remindersToListDto(
                listReminders().stream().filter(r -> r.getRemind().toLocalDate().equals(date)).collect(Collectors.toList()));
    }

    public List<ReminderDto> getMyReminders(String user_id) throws UserNotFoundException {
       return mapper.remindersToListDto(reminderRepository.findRemindersByUser(userService.findById(user_id)));
    }

    public Page<ReminderDto> getMyRemindersWithPage(int total, int current, String user_id)
            throws ReminderNotFoundException, UserNotFoundException {
        List<Reminder> myReminders = reminderRepository.findRemindersByUser(userService.findById(user_id));
        if (myReminders.isEmpty()) {
            throw new ReminderNotFoundException("Напоминания не найдены!");
        }
        Pageable pageable = PageRequest.of(current, myReminders.size()/total);
        return mapper.reminderToPageDto(reminderRepository.findAll(pageable));
    }

    //Намудрил???
    public void updateReminder(Long id, Reminder reminder, String user_id)
            throws ReminderNotFoundException, UserNotFoundException, NoAccessException, IllegalAccessException {
        Reminder reminderTemp = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Напоминание с ID:" + id + " не найдено!"));

        if (!reminderTemp.getUser().equals(userService.findById(user_id))) {
            throw new NoAccessException("Нет доступа! Вы не являетесь создателем данного напоминания!");
        }

        Field[] fields = reminder.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.getName().equals("title") || field.getName().equals("description") ||
                    field.getName().equals("remind")) {
                field.setAccessible(true);
                Object value = field.get(reminder);
                if (value != null) {
                    field.set(reminderTemp, value);
                }
            }
        }
        reminderRepository.save(reminderTemp);
    }
}
