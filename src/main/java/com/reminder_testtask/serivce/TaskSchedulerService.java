package com.reminder_testtask.serivce;

import com.reminder_testtask.entity.Reminder;
import com.reminder_testtask.entity.User;
import com.reminder_testtask.exception.ReminderNotFoundException;
import com.reminder_testtask.utils.TgBotSender;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskSchedulerService {

    private final ReminderServiceImpl reminderServiceImpl;
    private final JavaMailSender javaMailSender;
    private final TgBotSender tgBotSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Scheduled(fixedDelay = 10000)
    public void checkReminders() throws ReminderNotFoundException, AddressException, TelegramApiException {
        List<Reminder> reminders = reminderServiceImpl.listReminders();
        LocalDateTime now = LocalDateTime.now();

        for (Reminder reminder : reminders) {
            if (reminder.getRemind().isBefore(now)) {
                User user = reminder.getUser();
                if (!user.getEmail().isEmpty()) {
                    sendEmail(user.getEmail(), reminder.getTitle(), reminder.getDescription());
                }
                if (user.isActiveNotificationsTg()) {
                    tgBotSender.sendMessage(Long.parseLong(user.getTelegramId()),
                            reminder.getTitle() + "\n" + reminder.getDescription());
                }
                System.out.println("Reminder: " + reminder.getTitle());
                reminderServiceImpl.deleteReminderById(reminder.getId());
            }
        }
    }

    public void sendEmail(String to, String subject, String text) throws AddressException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(String.valueOf(new InternetAddress(sender)));
        javaMailSender.send(message);
    }
}
