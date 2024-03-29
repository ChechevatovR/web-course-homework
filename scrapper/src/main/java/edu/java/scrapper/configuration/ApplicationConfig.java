package edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class ApplicationConfig {
    @NotNull
    Scheduler scheduler;

    @NotNull
    Clients clients = new Clients();

    public Scheduler getScheduler() {
        return scheduler;
    }

    void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Clients getClients() {
        return clients;
    }

    void setClients(Clients clients) {
        this.clients = clients;
    }
}
