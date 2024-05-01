package com.reminder_testtask.utils;

import com.reminder_testtask.entity.Reminder;
import com.reminder_testtask.dto.UserDto;
import com.reminder_testtask.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto userToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .locale(user.getLocale())
                .telegramId(user.getTelegramId())
                .lastVisit(user.getLastVisit())
                .remindersNames(user.getReminders()
                        .stream()
                        .map(Reminder::getTitle)
                        .collect(Collectors.toList()))
                .build();
    }

    public List<UserDto> usersToListDto(List<User> users){
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }
}
