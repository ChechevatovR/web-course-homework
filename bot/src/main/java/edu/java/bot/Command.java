package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import java.util.function.Consumer;

public class Command {

    public static final int DEFAULT_ORDER = 0;
    public final String name;
    public final String description;
    public final String usage;
    public final int order;
    public final Consumer<Update> consumer;

    public Command(
        String name,
        String description,
        String usage,
        int order,
        Consumer<Update> consumer
    ) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.order = order;
        this.consumer = consumer;
    }

    public Command(
        String name,
        String description,
        String usage,
        Consumer<Update> consumer
    ) {
        this(name, description, usage, DEFAULT_ORDER, consumer);
    }

    public Command(
        String name,
        String description,
        int order,
        Consumer<Update> consumer
    ) {
        this(name, description, null, order, consumer);
    }

    public Command(
        String name,
        String description,
        Consumer<Update> consumer
    ) {
        this(name, description, null, DEFAULT_ORDER, consumer);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("/" + name);
        if (usage != null && !usage.isBlank()) {
            sb.append(" ").append(usage);
        }
        if (description != null && !description.isBlank()) {
            sb.append(" - ").append(description);
        }
        return sb.toString();
    }

    public com.pengrad.telegrambot.model.BotCommand asBotCommand() {
        return new com.pengrad.telegrambot.model.BotCommand(name, description);
    }
}
