package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.exceptions.BadCommandUsage;
import edu.java.bot.exceptions.BotException;
import edu.java.bot.exceptions.MethodHandlerInvocationException;
import edu.java.bot.exceptions.MethodHandlerSignatureException;
import edu.java.bot.exceptions.NoCommandInMessage;
import edu.java.bot.exceptions.UnknownCommand;
import edu.java.bot.exceptions.UnsupportedUpdate;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
                processOneUpdate(update);
            } catch (BotException e) {
                handleException(update, e);
            } catch (RuntimeException e) {
                handleException(update, new MethodHandlerInvocationException(e));
            }
        }
        return CONFIRMED_UPDATES_ALL;
    }

    public void processOneUpdate(Update update) {
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
    }

    public void handleException(Update update, BotException exception) {
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
            case BadCommandUsage e -> {
                sendMessage(update, "Ошибка вызова команды: " + e);
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
                log.trace("Received unsupported update: " + e.update);
            }
        }
    }
}
