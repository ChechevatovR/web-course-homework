package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.annotations.BotCommand;
import edu.java.bot.clients.scrapper.ScrapperApi;
import edu.java.bot.clients.scrapper.model.AddLinkRequest;
import edu.java.bot.clients.scrapper.model.LinkResponse;
import edu.java.bot.clients.scrapper.model.ListLinksResponse;
import edu.java.bot.clients.scrapper.model.RemoveLinkRequest;
import edu.java.bot.exceptions.BadCommandUsage;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Description;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UpdateNotifierListener extends AutoHelpListener {
    public final ScrapperApi scrapper;

    public UpdateNotifierListener(TelegramBot telegramBot, ScrapperApi scrapper) {
        super(telegramBot);
        this.scrapper = scrapper;
    }

    @NotNull
    private static URI getUriFromUpdate(Update update) {
        String[] splitted = update.message().text().split(" ", 2);
        if (splitted.length != 2) {
            throw new BadCommandUsage("URL не найден в сообщении");
        }
        URI url;
        try {
            url = new URI(splitted[1]);
        } catch (URISyntaxException e) {
            throw new BadCommandUsage("Некорректный URL", e);
        }
        return url;
    }

    private static <T> RuntimeException buildUnexpectedResponseException(ResponseEntity<T> response) {
        return new RuntimeException("Unexpected response from server: " + response);
    }

    @BotCommand("start")
    @Description("Зарегистрироваться в боте")
    @Order(Ordered.LOWEST_PRECEDENCE - 1)
    public String onStart(Update update) {
        Mono<ResponseEntity<Void>> mono = scrapper.tgChatIdPostWithHttpInfo(update.message().chat().id());
        ResponseEntity<Void> response = mono.block();
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode == HttpStatus.ACCEPTED) {
            return "Вы уже были зарегистрированы";
        }
        if (statusCode == HttpStatus.OK) {
            return "Успешная регистрация";
        }
        throw buildUnexpectedResponseException(response);
    }

    @BotCommand(value = "track", args = "<url>")
    @Order(Ordered.HIGHEST_PRECEDENCE + 9)
    @Description("Начать отслеживание содержимого по ссылке")
    public String onTrack(Update update) {
        Long chatId = update.message().chat().id();
        URI url = getUriFromUpdate(update);
        Mono<ResponseEntity<LinkResponse>> mono =
            scrapper.linksPostWithHttpInfo(chatId, new AddLinkRequest().link(url));
        ResponseEntity<LinkResponse> response = mono.block();
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode == HttpStatus.ACCEPTED) {
            return "Вы уже отслеживаете эту ссылку";
        }
        if (statusCode == HttpStatus.OK) {
            return "Ссылка добавлена";
        }
        throw buildUnexpectedResponseException(response);
    }

    @BotCommand(value = "untrack", args = "<url>")
    @Order(Ordered.HIGHEST_PRECEDENCE + 10)
    @Description("Прекратить отслеживание содержимого по ссылке")
    public String onUntrack(Update update) {
        Long chatId = update.message().chat().id();
        URI url = getUriFromUpdate(update);
        Mono<ResponseEntity<LinkResponse>> mono =
            scrapper.linksDeleteWithHttpInfo(chatId, new RemoveLinkRequest().link(url));
        ResponseEntity<LinkResponse> response = mono.block();
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode == HttpStatus.ACCEPTED) {
            return "Вы не отслеживаете эту ссылку";
        }
        if (statusCode == HttpStatus.OK) {
            return "Ссылка удалена";
        }
        throw buildUnexpectedResponseException(response);
    }

    @BotCommand("list")
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Description("Возвращает список отслеживаемых вами ссылок")
    public String onList(Update update) {
        Long chatId = update.message().chat().id();
        Mono<ResponseEntity<ListLinksResponse>> mono = scrapper.linksGetWithHttpInfo(chatId);
        ResponseEntity<ListLinksResponse> response = mono.block();
        ListLinksResponse links = response.getBody();
        if (links.getLinks().isEmpty()) {
            return "Список пуст";
        } else {
            String message = links.getLinks().stream()
                .map(LinkResponse::getUrl)
                .map(Objects::toString)
                .map(s -> "- " + s)
                .collect(Collectors.joining(System.lineSeparator()));
            return message;
        }
    }
}
