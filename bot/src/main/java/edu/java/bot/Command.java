package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Command {

    public static final int DEFAULT_ORDER = 0;
    public final String name;
    public final String description;
    public final List<String> args;
    public final int order;
    public final Consumer<Update> consumer;

    public Command(
        String name,
        String description,
        String[] args,
        int order,
        Consumer<Update> consumer
    ) {
        this.name = name;
        this.description = description;
        this.args = Arrays.stream(args).toList();
        this.order = order;
        this.consumer = consumer;
    }

    public Command(
        String name,
        String description,
        String[] args,
        Consumer<Update> consumer
    ) {
        this(name, description, args, DEFAULT_ORDER, consumer);
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
        if (args != null && !args.isEmpty()) {
            args.stream().forEach(arg -> sb.append(" `").append(arg).append('`'));
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
