package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.annotations.BotCommand;
import edu.java.bot.exceptions.*;
import org.springframework.context.annotation.Description;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractListener implements UpdatesListener {
    public final TelegramBot bot;
    protected final Map<String, Command> commands;

    public AbstractListener(TelegramBot bot) {
        this.bot = bot;
        bot.setUpdatesListener(this);
        commands = new BotCommandBuilder(this).buildCommands();
        com.pengrad.telegrambot.model.BotCommand[] botCommands = commands.values().stream()
            .map(Command::asBotCommand)
            .toArray(com.pengrad.telegrambot.model.BotCommand[]::new);
        bot.execute(new SetMyCommands(botCommands));
    }

    public final void sendMessage(Update update, String textMarkdown) {
        SendMessage sendMessageRequest = new SendMessage(update.message().chat().id(), textMarkdown);
        sendMessageRequest.parseMode(ParseMode.Markdown);
        bot.execute(sendMessageRequest);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            try {
                if (update.message() == null || update.message().text() == null) {
                    throw new UnsupportedUpdate(update);
                }
                Message message = update.message();
                String text = message.text();
                String commandSlashed = text.split(" ", 2)[0];
                if (!commandSlashed.startsWith("/")) {
                    throw new NoCommandInMessage(update);
                }
                String commandName = commandSlashed.substring(1);
                Command command = commands.get(commandName);
                if (command == null) {
                    throw new UnknownCommand(commandName);
                }
                command.consumer.accept(update);
            } catch (BotException e) {
                onException(update, e);
            } catch (RuntimeException e) {
                onException(update, new MethodHandlerInvocationException(e));
            }
        }
        return CONFIRMED_UPDATES_ALL;
    }

    public void onException(Update update, BotException exception) {
        switch (exception) {
            case UnknownCommand e -> {
                String responseText =
                    "Неизвестная команда: "
                    + e.command
                    + System.lineSeparator()
                    + "Используйте /help для получения списка команд";
                sendMessage(update, responseText);
            }
            case NoCommandInMessage e -> {
                sendMessage(update, "В сообщении не найдено команды");
            }
            case MethodHandlerInvocationException e -> {
                sendMessage(update, "В боте произошла ошибка: " + e.getCause());
            }
            case MethodHandlerSignatureException e -> {
                // Method signature must have been checked at startup
                // Not when message was received.
                throw new AssertionError("Unreachable");
            }
            case UnsupportedUpdate e -> {
                // no-op
            }
        }
    }
}
