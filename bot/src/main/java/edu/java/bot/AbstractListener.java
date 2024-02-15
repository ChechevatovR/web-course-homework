package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.annotations.BotCommand;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Description;

public abstract class AbstractListener implements UpdatesListener {
    public final TelegramBot bot;
    protected final Map<String, Command> commands = new HashMap<>();

    public AbstractListener(TelegramBot bot) {
        this.bot = bot;
        bot.setUpdatesListener(this);
        buildCommands();
        com.pengrad.telegrambot.model.BotCommand[] botCommands = commands.values().stream()
            .map(Command::asBotCommand)
            .toArray(com.pengrad.telegrambot.model.BotCommand[]::new);
        bot.execute(new SetMyCommands(botCommands));
    }

    private void buildCommands() {
        Class<? extends AbstractListener> clazz = getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            BotCommand commandAnnotation = method.getAnnotation(BotCommand.class);
            Description descriptionAnnotation = method.getAnnotation(Description.class);
            if (commandAnnotation == null) {
                continue;
            }
            Command command = new Command(
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

    private Consumer<Update> getConsumerForMethod(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
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
                Object[] args = prepareArgs(update, method);
                Object response = method.invoke(this, args);
                processResponse(update, response);
            } catch (IllegalAccessException e) {
                throw new AssertionError("Can't access public method", e);
            } catch (InvocationTargetException e) {
                sendMessage(update, "Произошла ошибка: " + e.getCause());
            }
        };
    }

    private Object[] prepareArgs(Update update, Method method) {
        return new Object[] {update};
    }

    private void processResponse(Update update, Object response) {
        if (response == null) {
            // Likely, method's return type was void.
            // No need to do anything
        } else if (response instanceof String responseString) {
            sendMessage(update, responseString);
        } else {
            throw new AssertionError("Unknown response type");
        }
    }

    protected final void sendMessage(Update update, String textMarkdown) {
        SendMessage sendMessageRequest = new SendMessage(update.message().chat().id(), textMarkdown);
        sendMessageRequest.parseMode(ParseMode.Markdown);
        bot.execute(sendMessageRequest);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            Message message = update.message();
            String text = message.text();
            String commandSlashed = text.split(" ", 2)[0];
            if (!commandSlashed.startsWith("/")) {
                sendMessage(update, "В сообщении не найдено команды");
                continue;
            }
            String commandName = commandSlashed.substring(1);
            Command command = commands.get(commandName);
            if (command == null) {
                onUnknownCommand(update, commandName);
                continue;
            }
            command.consumer.accept(update);
        }
        return CONFIRMED_UPDATES_ALL;
    }

    public void onUnknownCommand(Update update, String commandName) {
        String responseText =
            "Неизвестная команда: "
            + commandName
            + System.lineSeparator()
            + "Используйте /help для получения списка команд";
        sendMessage(update, responseText);
    }

    @BotCommand("help")
    @Description("Возвращает список доступных команд")
    public String onHelp(Update update) {
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

        // TODO add usage arguments for commands
        // public final String usage;
        public final Consumer<Update> consumer;

        public Command(String name, String description, Consumer<Update> consumer) {
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
