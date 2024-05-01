package com.reminder_testtask.controller;

import com.reminder_testtask.exception.UserNotFoundException;
import com.reminder_testtask.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<?> all() throws UserNotFoundException {
        return ResponseEntity.ok(userService.all());
    }


    @GetMapping("/addTelegramId/{id}")
    public ResponseEntity<?> addUserTelegramId(@PathVariable String id, Principal principal)
            throws UserNotFoundException {
        userService.addUserTelegramId(id, principal.getName());
        return ResponseEntity.ok("Пользователю с ID:" + principal.getName() +
                " добавлен TELEGRAM_ID!");
    }

    @GetMapping("/addEmail/{email}")
    public ResponseEntity<?> addUserEmail(@PathVariable String email, Principal principal)
            throws UserNotFoundException {
        userService.addUserEmail(email, principal.getName());
        return ResponseEntity.ok("Пользователю с ID:" + principal.getName() +
                " добавлен EMAIL!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.findUserById(id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) throws UserNotFoundException {
        userService.deleteUserById(id);
        return ResponseEntity.ok("Пользователь с ID:" + id + " удален!");
    }
}
