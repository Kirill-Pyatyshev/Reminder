package com.reminder_testtask.repository;

import com.reminder_testtask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByTelegramId(String telegram_id);
}
