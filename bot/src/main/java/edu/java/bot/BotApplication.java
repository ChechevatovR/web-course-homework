package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Bean
    public TelegramBot telegramBot(ApplicationConfig applicationConfig) {
        return new TelegramBot(applicationConfig.telegramToken);
    }
}
