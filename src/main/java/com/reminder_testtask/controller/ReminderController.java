package com.reminder_testtask.controller;

import com.reminder_testtask.entity.Reminder;
import com.reminder_testtask.exception.NoAccessException;
import com.reminder_testtask.exception.ReminderNotFoundException;
import com.reminder_testtask.exception.UserNotFoundException;
import com.reminder_testtask.serivce.ReminderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderServiceImpl reminderServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<?> createReminder(@RequestBody Reminder reminder, Principal principal)
            throws UserNotFoundException {
        reminderServiceImpl.saveReminder(reminder, principal.getName());
        return ResponseEntity.ok("Напоминание " + reminder.getTitle() + "создано!");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editReminder(
            @PathVariable Long id, @RequestBody Reminder reminder, Principal principal)
            throws UserNotFoundException, NoAccessException, ReminderNotFoundException, IllegalAccessException {
        reminderServiceImpl.updateReminder(id, reminder, principal.getName());
        return ResponseEntity.ok("Напоминание " + reminder.getTitle() + "обновлено!");
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> all() throws ReminderNotFoundException {
        return ResponseEntity.ok(reminderServiceImpl.getReminders());
    }

    @GetMapping(value = "/all/page/{total}/{current}")
    public ResponseEntity<?> allWithPage(@PathVariable int total, @PathVariable int current)
            throws ReminderNotFoundException {
        return ResponseEntity.ok(reminderServiceImpl.getRemindersWithPage(total, current));
    }

    @GetMapping(value = "/all/my/page/{total}/{current}")
    public ResponseEntity<?> allMyReminderWithPage(
            Principal principal, @PathVariable int total, @PathVariable int current)
            throws UserNotFoundException, ReminderNotFoundException {
        return ResponseEntity.ok(reminderServiceImpl.getMyRemindersWithPage(total, current, principal.getName()));
    }

    @GetMapping(value = "/all/my")
    public ResponseEntity<?> allMyReminder(Principal principal) throws UserNotFoundException {
        return ResponseEntity.ok(reminderServiceImpl.getMyReminders(principal.getName()));
    }

    @GetMapping(value = "/sort/{sortingBy}")
    public ResponseEntity<?> sortReminders(@PathVariable String sortingBy) throws ReminderNotFoundException {
        return ResponseEntity.ok(reminderServiceImpl.sortReminder(sortingBy));
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<?> getReminderById(@PathVariable Long id) throws ReminderNotFoundException {
        return ResponseEntity.ok(reminderServiceImpl.findReminderById(id));
    }

    @GetMapping("/search/title/{title}")
    public ResponseEntity<?> getReminderByTitle(@PathVariable String title) throws ReminderNotFoundException {
        return ResponseEntity.ok(reminderServiceImpl.findReminderByTitle(title));
    }

    @GetMapping("/search/description/{description}")
    public ResponseEntity<?> getReminderByDescription(@PathVariable String description)
            throws ReminderNotFoundException {
        return ResponseEntity.ok(reminderServiceImpl.findReminderByDescription(description));
    }

    @GetMapping("/search/date/{date}")
    public ResponseEntity<?> getReminderByDate(@PathVariable LocalDate date)
            throws ReminderNotFoundException {
        return ResponseEntity.ok(reminderServiceImpl.findReminderByDate(date));
    }

    @GetMapping("/filter/{date}")
    public ResponseEntity<?> getReminderWithFilter(@PathVariable LocalDate date)
            throws ReminderNotFoundException {
        return ResponseEntity.ok(reminderServiceImpl.filterByDate(date));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReminderById(@PathVariable Long id)
            throws ReminderNotFoundException {
        reminderServiceImpl.deleteReminderById(id);
        return ResponseEntity.ok("Напоминание с ID:" + id + " удалено!");
    }
}
