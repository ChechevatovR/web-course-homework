package edu.java.bot.exceptions;

public final class BadCommandUsage extends BotException {
    public BadCommandUsage(String message) {
        super(message);
    }

    public BadCommandUsage(String message, Throwable cause) {
        super(message, cause);
    }
}
