package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.annotations.BotCommand;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

@Component
public class UpdateNotifierListener extends AbstractListener {
    public UpdateNotifierListener(final TelegramBot telegramBot) {
        super(telegramBot);
    }

    @Override
    public String onUnknownCommand(final Update update) {
        return """
               Неизвестная команда.
               Используйте /help для получения списка команд
               """;
    }

    @BotCommand("start")
    @Description("Зарегистрироваться в боте")
    public String onStart(final Update update) {
        return "onStart";
    }

    @BotCommand("track")
    @Description("Начать отслеживание содержимого по ссылке")
    public String onTrack(final Update update) {
        return "onTrack";
    }

    @BotCommand("untrack")
    @Description("Прекратить отслеживание содержимого по ссылке")
    public String onUntrack(final Update update) {
        return "onUntrack";
    }

    @BotCommand("list")
    @Description("Возвращает список отслеживаемых вами ссылок")
    public String onList(final Update update) {
        return "Пусто";
    }
}
