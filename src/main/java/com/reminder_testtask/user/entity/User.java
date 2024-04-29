package com.reminder_testtask.user.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.reminder_testtask.reminder.entity.Reminder;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "locale")
    private String locale;
    @Column(name = "last_visit")
    private LocalDateTime lastVisit;
    @Column(name = "telegram_id")
    private String telegramId;
    @Column(name = "active_notifications_tg")
    private boolean activeNotificationsTg;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", locale='" + locale + '\'' +
                ", lastVisit=" + lastVisit +
                ", telegramId='" + telegramId + '\'' +
                ", activeNotificationsTg=" + activeNotificationsTg +
                '}';
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Reminder> reminders = new ArrayList<>();
}

