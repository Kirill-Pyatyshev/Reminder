package com.reminder_testtask.user.controller;

import com.reminder_testtask.exception.UserNotFoundException;
import com.reminder_testtask.user.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<?> all() {
        try{
            return ResponseEntity.ok(userService.all());
        }catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    //??????????
    @GetMapping("/addTelegramId/{id}")
    public ResponseEntity<?> addUserTelegramId(@PathVariable String id, Principal principal) {
        try{
            userService.addUserTelegramId(id, principal.getName());
            return ResponseEntity.ok("Пользователь с ID:" + principal.getName() +
                    " добавлен TELEGRAM_ID!");
        }catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @GetMapping("/addEmail/{email}")
    public ResponseEntity<?> addUserEmail(@PathVariable String email, Principal principal) {
        try{
            userService.addUserEmail(email, principal.getName());
            return ResponseEntity.ok("Пользователю с ID:" + principal.getName() +
                    " добавлен EMAIL!");
        }catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try{
            return ResponseEntity.ok(userService.findUserById(id));
        }catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id){
        try{
            userService.deleteUserById(id);
            return ResponseEntity.ok("Пользователь с ID:" + id + " удален!");
        }catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Непредвиденная ошибка!\n" + e.getMessage());
        }
    }
}
