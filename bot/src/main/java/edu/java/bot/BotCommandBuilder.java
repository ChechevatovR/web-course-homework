package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.annotations.BotCommand;
import edu.java.bot.exceptions.MethodHandlerInvocationException;
import edu.java.bot.exceptions.MethodHandlerSignatureException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.springframework.context.annotation.Description;
import org.springframework.core.annotation.Order;

public class BotCommandBuilder {
    public final AbstractListener listener;

    public BotCommandBuilder(AbstractListener listener) {
        this.listener = listener;
    }

    public Map<String, Command> buildCommands() {
        Map<String, Command> commands = new HashMap<>();

        Class<?> clazz = listener.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            BotCommand commandAnnotation = method.getAnnotation(BotCommand.class);
            if (commandAnnotation == null) {
                continue;
            }
            Description descriptionAnnotation = method.getAnnotation(Description.class);
            Order orderAnnotation = method.getAnnotation(Order.class);
            Command command = new Command(
                commandAnnotation.value(),
                descriptionAnnotation == null ? null : descriptionAnnotation.value(),
                commandAnnotation.args(),
                orderAnnotation == null ? Command.DEFAULT_ORDER : orderAnnotation.value(),
                getConsumerForMethod(method)
            );
            if (commands.put(commandAnnotation.value(), command) != null) {
                throw new MethodHandlerSignatureException(
                    "Found multiple handlers for command " + commandAnnotation.value(),
                    clazz
                );
            }
        }
        return commands;
    }

    private Consumer<Update> getConsumerForMethod(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1 || parameterTypes[0] != Update.class) {
            throw new MethodHandlerSignatureException(
                "Bot command methods must accept exactly one argument, of type " + Update.class.getName(),
                method
            );
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new MethodHandlerSignatureException(
                "Bot command methods must be public",
                method
            );
        }
        if (!Set.of("void", "java.lang.String").contains(method.getReturnType().getName())) {
            throw new MethodHandlerSignatureException(
                "Bot command method's return type is not supported",
                method
            );
        }
        return (final Update update) -> {
            try {
                Object[] args = prepareArgs(update, method);
                Object response = method.invoke(listener, args);
                processResponse(update, response);
            } catch (IllegalAccessException e) {
                throw new AssertionError("Can't access public method", e);
            } catch (InvocationTargetException e) {
                listener.onException(update, new MethodHandlerInvocationException(e));
            }
        };
    }

    private Object[] prepareArgs(Update update, Method method) {
        // Сюда в будущем можно добавить подготовку аргументов для более сложных команд
        // Например, разбор сообщения для команд, содержащих аргументы
        return new Object[] {update};
    }

    private void processResponse(Update update, Object response) {
        if (response == null) {
            // Likely, method's return type was void.
            // No need to do anything
        } else if (response instanceof String responseString) {
            listener.sendMessage(update, responseString);
        } else {
            throw new AssertionError("Unknown response type");
        }
    }
}
