package com.reminder_testtask.serivce;

import com.reminder_testtask.dto.ReminderDto;
import com.reminder_testtask.entity.Reminder;
import com.reminder_testtask.exception.NoAccessException;
import com.reminder_testtask.exception.ReminderNotFoundException;
import com.reminder_testtask.exception.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ReminderService {

    void saveReminder(Reminder reminder, String user_id) throws UserNotFoundException;

    void updateReminder(Long id, Reminder reminder, String user_id)
            throws ReminderNotFoundException, UserNotFoundException, NoAccessException, IllegalAccessException;

    void deleteReminderById(Long id) throws ReminderNotFoundException;

    List<Reminder> listReminders();

    List<ReminderDto> getReminders() throws ReminderNotFoundException;

    Page<ReminderDto> getRemindersWithPage(int total, int current) throws ReminderNotFoundException;

    List<ReminderDto> getMyReminders(String user_id) throws UserNotFoundException;

    Page<ReminderDto> getMyRemindersWithPage(int total, int current, String user_id)
            throws ReminderNotFoundException, UserNotFoundException;

    ReminderDto findReminderById(Long id) throws ReminderNotFoundException;

    ReminderDto findReminderByTitle(String title) throws ReminderNotFoundException;

    ReminderDto findReminderByDate(LocalDate date) throws ReminderNotFoundException;

    List<ReminderDto> sortReminder(String sortingBy) throws ReminderNotFoundException;

    List<ReminderDto> filterByDate(LocalDate date) throws ReminderNotFoundException;
}
