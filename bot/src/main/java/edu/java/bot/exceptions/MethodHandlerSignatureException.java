package edu.java.bot.exceptions;

import java.lang.reflect.Method;

public final class MethodHandlerSignatureException extends BotException {
    public final Class<?> clazz;
    public final Method method;

    public MethodHandlerSignatureException(String message, Class<?> clazz) {
        super(
            message
            + System.lineSeparator()
            + "Class: " + clazz.getName()
        );
        this.clazz = clazz;
        this.method = null;
    }

    public MethodHandlerSignatureException(String message, Method method) {
        super(
            message
            + System.lineSeparator()
            + "Method: " + method.getName()
        );
        this.clazz = method.getDeclaringClass();
        this.method = method;
    }
}
