package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.telegram", ignoreUnknownFields = false)
public record TelegramConfig(

    @NotEmpty
    String apiToken
) {
}
