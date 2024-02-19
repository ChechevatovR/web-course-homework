package edu.java.bot.exceptions;

public abstract sealed class BotException extends RuntimeException
    permits MethodHandlerInvocationException, MethodHandlerSignatureException, NoCommandInMessage, UnknownCommand,
    UnsupportedUpdate {
    protected BotException() {
    }

    public BotException(String message) {
        super(message);
    }

    public BotException(String message, Throwable cause) {
        super(message, cause);
    }

    public BotException(Throwable cause) {
        super(cause);
    }
}
