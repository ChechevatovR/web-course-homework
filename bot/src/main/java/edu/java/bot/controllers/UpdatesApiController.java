package edu.java.bot.controllers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.controllers.model.LinkUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class UpdatesApiController implements UpdatesApi {
    public final TelegramBot bot;

    public UpdatesApiController(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdate linkUpdate) {
        String messageText = buildMessageForUpdate(linkUpdate);
        for (Long chatId : linkUpdate.getTgChatIds()) {
            bot.execute(new SendMessage(chatId, messageText));
        }
        return ResponseEntity.ok(null);
    }

    protected String buildMessageForUpdate(LinkUpdate update) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ресурс по ссылке ")
            .append(update.getUrl())
            .append(" был обновлен.")
            .append(System.lineSeparator())
            .append("Обновление: ")
            .append(update.getDescription());
        return sb.toString();
    }
}
