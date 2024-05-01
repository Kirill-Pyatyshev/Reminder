package com.reminder_testtask.utils;

import com.reminder_testtask.configuration.TgBotConfig;
import com.reminder_testtask.exception.UserNotFoundException;
import com.reminder_testtask.entity.User;
import com.reminder_testtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TgBotSender extends TelegramLongPollingBot {

    @Autowired
    private TgBotConfig botConfig;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chat_id = update.getMessage().getChatId();
            System.out.println(chat_id);
            String messageText = update.getMessage().getText();
            switch (messageText) {
                case "/start":
                    try {
                        startCommandReceived(chat_id, update.getMessage().getChat().getFirstName());

                        // Полная каша, надо поменять
                        User user = userRepository.findByTelegramId(String.valueOf(chat_id))
                                .orElseThrow(()-> {
                                    try {
                                        sendMessage(chat_id,
                                                "You do not have ID_TELEGRAM specified" +
                                                        " in the reminders application!");
                                        return new UserNotFoundException("Пользователя с ID_TELEGRAM:"
                                                + chat_id + " не найдено!");
                                    } catch (TelegramApiException e) {
                                        throw new RuntimeException(e);
                                    }});
                        user.setActiveNotificationsTg(true);
                        userRepository.save(user);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    } catch (UserNotFoundException e) {
                        throw new RuntimeException(e);
                    }
            }
        }
    }

    private void startCommandReceived(long chatId, String name) throws TelegramApiException {
        String answer = "Hi, " + name + ", нou have enabled reminders via telegram!";
        sendMessage(chatId, answer);
    }

    public void sendMessage(long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        execute(message);
    }

}
