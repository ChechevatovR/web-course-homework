package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.annotations.BotCommand;
import org.springframework.context.annotation.Description;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class AbstractListener implements UpdatesListener {
    public final TelegramBot bot;
    protected final Map<String, Command> commands = new HashMap<>();

    public AbstractListener(final TelegramBot bot) {
        this.bot = bot;
        bot.setUpdatesListener(this);
        buildCommands();
        com.pengrad.telegrambot.model.BotCommand[] botCommands = commands.values().stream()
            .map(Command::asBotCommand)
            .toArray(com.pengrad.telegrambot.model.BotCommand[]::new);
        bot.execute(new SetMyCommands(botCommands));
    }

    private void buildCommands() {
        final Method[] methods = getClass().getMethods();
        for (final Method method : methods) {
            final BotCommand commandAnnotation = method.getAnnotation(BotCommand.class);
            final Description descriptionAnnotation = method.getAnnotation(Description.class);
            if (commandAnnotation == null) {
                continue;
            }
            final Command command = new Command(
                commandAnnotation.value(),
                descriptionAnnotation == null ? null : descriptionAnnotation.value(),
                getConsumerForMethod(method)
            );
            if (commands.put(commandAnnotation.value(), command) != null) {
                throw new BotCommandMethodException(
                    "Found multiple handlers for command " + commandAnnotation.value(),
                    clazz
                );
            }
        }
    }

    private Consumer<Update> getConsumerForMethod(final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1 || parameterTypes[0] != Update.class) {
            throw new BotCommandMethodException(
                "Bot command methods must accept exactly one argument, of type " + Update.class.getName(),
                method
            );
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new BotCommandMethodException(
                "Bot command methods must be public",
                method
            );
        }
        return (final Update update) -> {
            try {
                final Object[] args = prepareArgs(update, method);
                final Object response = method.invoke(this, args);
                processResponse(update, response);
            } catch (final IllegalAccessException e) {
                throw new AssertionError("Can't access public method", e);
            } catch (final InvocationTargetException e) {
                // TODO Catch and write to user
                throw new RuntimeException(e);
            }
        };
    }

    private Object[] prepareArgs(final Update update, final Method method) {
        return new Object[]{update};
    }

    private void processResponse(final Update update, final Object response) {
        if (response == null) {
            // Likely, method's return type was void.
            // No need to do anything
        } else if (response instanceof final String responseString) {
            final SendMessage sendMessageRequest = new SendMessage(update.message().chat().id(), responseString);
            sendMessageRequest.parseMode(ParseMode.Markdown);
            bot.execute(sendMessageRequest);
        } else {
            throw new AssertionError("Unknown return type");
        }
    }

    @Override
    public int process(final List<Update> updates) {
        for (final Update update : updates) {
            final Message message = update.message();
            final String text = message.text();
            final String commandSlashed = text.split(" ", 2)[0];
            if (!commandSlashed.startsWith("/")) {
                // TODO Send message to user
                System.err.println("Command in message not found: " + text);
                continue;
            }
            final String commandName = commandSlashed.substring(1);
            final Command command = commands.get(commandName);
            if (command == null) {
                // TODO Send message to user
                System.err.println("Unknown command in message: " + text);
                continue;
            }
            command.consumer.accept(update);
        }
        return CONFIRMED_UPDATES_ALL;
    }

    public abstract String onUnknownCommand(final Update update);

    @BotCommand("help")
    @Description("Возвращает список доступных команд")
    public String onHelp(final Update update) {
        return commands.values().stream()
            .map(Command::toString)
            .collect(Collectors.joining(System.lineSeparator()));
    }

    public static class BotCommandMethodException extends RuntimeException {
        public BotCommandMethodException(String message, Class<?> clazz) {
            super(
                message
                + System.lineSeparator()
                + "Class: " + clazz.getName()
            );
        }

        public BotCommandMethodException(String message, Method method) {
            super(
                message
                + System.lineSeparator()
                + "Method: " + method.getName()
            );
        }
    }

    public class Command {
        public final String name;
        public final String description;

        // add usage arguments for commands
//        public final String usage;
        public final Consumer<Update> consumer;

        public Command(final String name, final String description, final Consumer<Update> consumer) {
            this.name = name;
            this.description = description;
            this.consumer = consumer;
        }

        @Override
        public String toString() {
            if (description == null) {
                return "/" + name;
            } else {
                return "/" + name + " - " + description;
            }
        }

        public com.pengrad.telegrambot.model.BotCommand asBotCommand() {
            return new com.pengrad.telegrambot.model.BotCommand(name, description);
        }
    }
}
