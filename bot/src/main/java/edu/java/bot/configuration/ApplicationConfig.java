package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class ApplicationConfig {
    @NotEmpty
    String telegramToken;

    @NotNull
    Clients clients = new Clients();

    public String getTelegramToken() {
        return telegramToken;
    }

    void setTelegramToken(String telegramToken) {
        this.telegramToken = telegramToken;
    }

    public Clients getClients() {
        return clients;
    }

    void setClients(Clients clients) {
        this.clients = clients;
    }
}
