package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.configuration.TelegramConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, TelegramConfig.class})
public class BotApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Bean
    public TelegramBot telegramBot(TelegramConfig config) {
        return new TelegramBot(config.apiToken());
    }

    @Bean
    public UpdateNotifierListener updateNotifierListener(TelegramBot telegramBot) {
        return new UpdateNotifierListener(telegramBot);
    }
}
