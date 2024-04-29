package com.reminder_testtask.user.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String locale;
    private LocalDateTime lastVisit;
    private String telegramId;
    private boolean activeNotificationsTg;
    private List<String> remindersNames;

}
