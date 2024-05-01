package com.reminder_testtask.repository;

import com.reminder_testtask.entity.Reminder;
import com.reminder_testtask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    Optional<Reminder> findByTitle(String title);

    Optional<Reminder> findByDescription(String description);

    Optional<Reminder> findByRemind(LocalDate remind);

    List<Reminder> findRemindersByUser(User user);
}
