package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.annotations.BotCommand;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

@Component
public class UpdateNotifierListener extends AutoHelpListener {
    public UpdateNotifierListener(TelegramBot telegramBot) {
        super(telegramBot);
    }

    @BotCommand("start")
    @Description("Зарегистрироваться в боте")
    public String onStart(Update update) {
        return "onStart";
    }

    @BotCommand("track")
    @Description("Начать отслеживание содержимого по ссылке")
    public String onTrack(Update update) {
        return "onTrack";
    }

    @BotCommand("untrack")
    @Description("Прекратить отслеживание содержимого по ссылке")
    public String onUntrack(Update update) {
        return "onUntrack";
    }

    @BotCommand("list")
    @Description("Возвращает список отслеживаемых вами ссылок")
    public String onList(Update update) {
        return "onList";
    }
}
