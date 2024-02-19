package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.annotations.BotCommand;
import org.springframework.context.annotation.Description;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class UpdateNotifierListener extends AutoHelpListener {
    public UpdateNotifierListener(TelegramBot telegramBot) {
        super(telegramBot);
    }

    @BotCommand("start")
    @Description("Зарегистрироваться в боте")
    @Order(Ordered.LOWEST_PRECEDENCE - 1)
    public String onStart(Update update) {
        return "onStart";
    }

    @BotCommand(value = "track", args = "<url>")
    @Order(Ordered.HIGHEST_PRECEDENCE + 9)
    @Description("Начать отслеживание содержимого по ссылке")
    public String onTrack(Update update) {
        return "onTrack";
    }

    @BotCommand(value = "untrack", args = "<url>")
    @Order(Ordered.HIGHEST_PRECEDENCE + 10)
    @Description("Прекратить отслеживание содержимого по ссылке")
    public String onUntrack(Update update) {
        return "onUntrack";
    }

    @BotCommand("list")
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Description("Возвращает список отслеживаемых вами ссылок")
    public String onList(Update update) {
        return "onList";
    }
}
