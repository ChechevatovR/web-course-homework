package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.annotations.BotCommand;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Description;

public class AutoHelpListener extends AbstractListener {
    // TODO
    // Хочется обойтись без этого класса, и вынести его функционал в аннотацию.
    // Создать Annotation Processor, который бы генерировал этот метод onHelp.
    // Аналогично сделать автоматическую установку меню через апи телеграма.
    // Но таким кунг-фу я пока не владею.

    public AutoHelpListener(TelegramBot bot) {
        super(bot);
    }

    @BotCommand("help")
    @Description("Возвращает список доступных команд")
    public String onHelp(Update update) {
        return commands.values().stream()
            .map(Command::toString)
            .collect(Collectors.joining(System.lineSeparator()));
    }
}
