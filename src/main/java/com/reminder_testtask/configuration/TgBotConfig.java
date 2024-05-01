package com.reminder_testtask.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TgBotConfig {

    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String botToken;
}
