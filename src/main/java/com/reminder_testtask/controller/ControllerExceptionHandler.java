package com.reminder_testtask.controller;

import com.reminder_testtask.entity.ErrorMessage;
import com.reminder_testtask.exception.NoAccessException;
import com.reminder_testtask.exception.ReminderNotFoundException;
import com.reminder_testtask.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(ReminderNotFoundException.class)
    public ResponseEntity<ErrorMessage> reminderNotFoundException(ReminderNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<ErrorMessage> noAccessException(NoAccessException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessage(HttpStatus.FORBIDDEN, exception.getMessage(), LocalDateTime.now()));
    }

    //Не знаю какой статус кинуть в этом случаев
    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ErrorMessage> noAccessException(IllegalAccessException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessage(HttpStatus.NO_CONTENT, exception.getMessage(), LocalDateTime.now()));
    }

}
