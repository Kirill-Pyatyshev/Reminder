package com.reminder_testtask.reminder.dto;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReminderDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime remind;
    private String userName;

}
