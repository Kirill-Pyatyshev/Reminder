package com.reminder_testtask.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok("ReminderApp");
    }
}
