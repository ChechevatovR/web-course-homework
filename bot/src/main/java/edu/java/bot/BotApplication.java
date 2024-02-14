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
    public static void main(final String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Bean
    public TelegramBot telegramBot(final ApplicationConfig config) {
        return new TelegramBot(config.telegramToken());
    }

    @Bean
    public UpdateNotifierListener updateNotifierListener(final TelegramBot telegramBot) {
        return new UpdateNotifierListener(telegramBot);
    }
}
