package edu.java.bot.exceptions;

import com.pengrad.telegrambot.model.Update;

public final class NoCommandInMessage extends BotException {
    public final Update update;

    public NoCommandInMessage(Update update) {
        this.update = update;
    }

    public NoCommandInMessage(Update update, Throwable cause) {
        super(cause);
        this.update = update;
    }
}
