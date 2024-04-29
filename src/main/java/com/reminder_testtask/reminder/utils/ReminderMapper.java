package com.reminder_testtask.reminder.utils;

import com.reminder_testtask.reminder.dto.ReminderDto;
import com.reminder_testtask.reminder.entity.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReminderMapper {

    public ReminderDto reminderToDto(Reminder reminder){
        return ReminderDto.builder()
                .id(reminder.getId())
                .title(reminder.getTitle())
                .description(reminder.getDescription())
                .remind(reminder.getRemind())
                .userName(reminder.getUser().getName())
                .build();
    }

    public List<ReminderDto> remindersToListDto(List<Reminder> reminders){
        return reminders.stream().map(this::reminderToDto).collect(Collectors.toList());
    }

    public Page<ReminderDto> reminderToPageDto(Page<Reminder> reminders){
        return new PageImpl<>(reminders.stream().map(this::reminderToDto).collect(Collectors.toList()));
    }
}
