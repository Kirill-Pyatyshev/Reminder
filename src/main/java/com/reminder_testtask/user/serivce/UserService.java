package com.reminder_testtask.user.serivce;

import com.reminder_testtask.exception.UserNotFoundException;
import com.reminder_testtask.user.dto.UserDto;
import com.reminder_testtask.user.entity.User;
import com.reminder_testtask.user.repository.UserRepository;
import com.reminder_testtask.user.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public List<UserDto> all() throws UserNotFoundException {
        if (listUsers().size() == 0) {
            throw new UserNotFoundException("Пользователи не найдены!");
        }
        return mapper.usersToListDto(listUsers());
    }

    public User findById(String id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID:" + id + " не найден!"));
    }

    public UserDto findUserById(String id) throws UserNotFoundException {
        return mapper.userToDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID:" + id + " не найден!")));
    }

    public void deleteUserById(String id) throws UserNotFoundException {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID:" + id + " не найден!"));
        userRepository.deleteById(id);
    }

    public void addUserTelegramId(String telegram_id , String user_id) throws UserNotFoundException {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID:" + user_id + " не найден!"));
        user.setTelegramId(telegram_id);
        userRepository.save(user);
    }

    public void addUserEmail(String email , String user_id) throws UserNotFoundException {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID:" + user_id + " не найден!"));
        user.setEmail(email);
        userRepository.save(user);
    }
}
