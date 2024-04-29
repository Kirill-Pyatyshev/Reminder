package com.reminder_testtask.reminder.controller;

import com.reminder_testtask.exception.NoAccessException;
import com.reminder_testtask.exception.ReminderNotFoundException;
import com.reminder_testtask.exception.UserNotFoundException;
import com.reminder_testtask.reminder.entity.Reminder;
import com.reminder_testtask.reminder.serivce.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/reminder")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping("/create")
    public ResponseEntity<?> createReminder(@RequestBody Reminder reminder, Principal principal) {
        try {
            reminderService.saveReminder(reminder, principal.getName());
            return ResponseEntity.ok("Напоминание " + reminder.getTitle() + "создано!");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editReminder(@PathVariable Long id, @RequestBody Reminder reminder, Principal principal) {
        try {
            reminderService.updateReminder(id, reminder, principal.getName());
            return ResponseEntity.ok("Напоминание " + reminder.getTitle() + "обновлено!");
        } catch (UserNotFoundException | NoAccessException | ReminderNotFoundException e) {
            throw new RuntimeException(e);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> all() {
        try{
            return ResponseEntity.ok(reminderService.getReminders());
        }catch (ReminderNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @GetMapping(value = "/all/page/{total}/{current}")
    public ResponseEntity<?> allWithPage(@PathVariable int total, @PathVariable int current) {
        try{
            return ResponseEntity.ok(reminderService.getRemindersWithPage(total,current));
        }catch (ReminderNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @GetMapping(value = "/all/my/page/{total}/{current}")
    public ResponseEntity<?> allMyReminderWithPage(Principal principal,
                                                   @PathVariable int total, @PathVariable int current) {
        try{
            return ResponseEntity.ok(reminderService.getMyRemindersWithPage(total, current ,principal.getName()));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }
    @GetMapping(value = "/all/my")
    public ResponseEntity<?> allMyReminder(Principal principal) {
        try{
            return ResponseEntity.ok(reminderService.getMyReminders(principal.getName()));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @GetMapping(value = "/sort/{sortingBy}")
    public ResponseEntity<?> sortReminders(@PathVariable String sortingBy) {
        try{
            return ResponseEntity.ok(reminderService.sortReminder(sortingBy));
        }catch (ReminderNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<?> getReminderById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(reminderService.findReminderById(id));
        }catch (ReminderNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @GetMapping("/search/title/{title}")
    public ResponseEntity<?> getReminderByTitle(@PathVariable String title) {
        try{
            return ResponseEntity.ok(reminderService.findReminderByTitle(title));
        }catch (ReminderNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @GetMapping("/search/description/{description}")
    public ResponseEntity<?> getReminderByDescription(@PathVariable String description) {
        try{
            return ResponseEntity.ok(reminderService.findReminderByDescription(description));
        }catch (ReminderNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @GetMapping("/search/date/{date}")
    public ResponseEntity<?> getReminderByDate(@PathVariable LocalDate date) {
        try{
            return ResponseEntity.ok(reminderService.findReminderByDate(date));
        }catch (ReminderNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @GetMapping("/filter/{date}")
    public ResponseEntity<?> getReminderWithFilter(@PathVariable LocalDate date) {
        try{
            return ResponseEntity.ok(reminderService.filterByDate(date));
        }catch (ReminderNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReminderById(@PathVariable Long id){
        try{
            reminderService.deleteReminderById(id);
            return ResponseEntity.ok("Напоминание с ID:" + id + " удалено!");
        }catch (ReminderNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }
}
