package edu.java.bot.exceptions;


public final class UnknownCommand extends BotException {
    public final String command;

    public UnknownCommand(String command) {
        this.command = command;
    }

    public UnknownCommand(String command, Throwable cause) {
        super(cause);
        this.command = command;
    }
}
