package edu.java.configuration;

import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
}
