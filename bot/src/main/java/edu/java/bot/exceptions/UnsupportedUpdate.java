package edu.java.bot.exceptions;

import com.pengrad.telegrambot.model.Update;

public final class UnsupportedUpdate extends BotException {
    public final Update update;

    public UnsupportedUpdate(Update update) {
        this.update = update;
    }
}
