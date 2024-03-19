package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.clients.scrapper.ScrapperApi;
import edu.java.bot.clients.scrapper.model.LinkResponse;
import edu.java.bot.clients.scrapper.model.ListLinksResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class TestUtils {
    public static final Random R = new Random(41);

    public static Update mockMessage(long chatId, String text) {
        Chat chatMock = Mockito.mock(Chat.class);
        Mockito.when(chatMock.id()).thenReturn(chatId);

        Message messageMock = Mockito.mock(Message.class);
        Mockito.when(messageMock.chat()).thenReturn(chatMock);
        Mockito.when(messageMock.text()).thenReturn(text);

        Update updateMock = Mockito.mock(Update.class);
        Mockito.when(updateMock.message()).thenReturn(messageMock);

        return updateMock;
    }

    public static Update mockMessage(String text) {
        return mockMessage(R.nextLong(), text);
    }

    public static TelegramBot mockBot() {
        TelegramBot mock = Mockito.mock(TelegramBot.class);
        return mock;
    }

    public static UpdateNotifierListener mockUpdateNotifierListener() {
        TelegramBot bot = mockBot();
        ScrapperApi api = mockScrapperApi();
        UpdateNotifierListener listener = new UpdateNotifierListener(bot, api);

        ArgumentCaptor<SetMyCommands> captor = ArgumentCaptor.forClass(SetMyCommands.class);
        Mockito.verify(bot).setUpdatesListener(listener);
        Mockito.verify(bot).execute(captor.capture());

        return listener;
    }

    private static WebClientResponseException buildException(HttpStatus status) {
        return WebClientResponseException.create(
            status.value(),
            status.getReasonPhrase(),
            new HttpHeaders(),
            new byte[0],
            StandardCharsets.UTF_8
        );
    }

    private static ScrapperApi mockScrapperApi() {
        ScrapperApi api = Mockito.mock(ScrapperApi.class);
        LinkResponse linkResponse = new LinkResponse().id(1L).url(URI.create("https://example.com"));
        Mockito.when(api.tgChatIdPostWithHttpInfo(Mockito.any())).thenAnswer(invocation -> {
            Long chatId = invocation.getArgument(0, Long.class);
            if (chatId == 200) {
                return Mono.just(ResponseEntity.ok(null));
            } else if (chatId == 202) {
                return Mono.just(ResponseEntity.status(HttpStatus.ACCEPTED).body(null));
            } else if (chatId == 400) {
                return Mono.error(buildException(HttpStatus.BAD_REQUEST));
            } else {
                throw new AssertionError("Unreachable");
            }
        });
        Mockito.when(api.linksDeleteWithHttpInfo(Mockito.any(), Mockito.any())).thenAnswer(invocation -> {
            Long chatId = invocation.getArgument(0, Long.class);
            if (chatId == 200) {
                return Mono.just(ResponseEntity.ok(linkResponse));
            } else if (chatId == 202) {
                return Mono.just(ResponseEntity.status(HttpStatus.ACCEPTED).body(linkResponse));
            } else if (chatId == 400) {
                return Mono.error(buildException(HttpStatus.BAD_REQUEST));
            } else if (chatId == 404) {
                return Mono.error(buildException(HttpStatus.NOT_FOUND));
            } else {
                throw new AssertionError("Unreachable");
            }
        });
        Mockito.when(api.linksPostWithHttpInfo(Mockito.any(), Mockito.any())).thenAnswer(invocation -> {
            Long chatId = invocation.getArgument(0, Long.class);
            if (chatId == 200) {
                return Mono.just(ResponseEntity.ok(linkResponse));
            } else if (chatId == 202) {
                return Mono.just(ResponseEntity.status(HttpStatus.ACCEPTED).body(linkResponse));
            } else if (chatId == 400) {
                return Mono.error(buildException(HttpStatus.BAD_REQUEST));
            } else if (chatId == 404) {
                return Mono.error(buildException(HttpStatus.NOT_FOUND));
            } else {
                throw new AssertionError("Unreachable");
            }
        });
        Mockito.when(api.linksGetWithHttpInfo(Mockito.any())).thenAnswer(invocation -> {
            Long chatId = invocation.getArgument(0, Long.class);
            ListLinksResponse response = new ListLinksResponse();
            if (200 <= chatId && chatId < 300) {
                for (int i = 0; i < chatId - 200; i++) {
                    LinkResponse item =
                        new LinkResponse().id((long) i).url(URI.create("https://example.com/" + i));
                    response.addLinksItem(item);
                }
                response.size((int) (chatId - 200));
                return Mono.just(ResponseEntity.ok(response));
            } else if (chatId == 400) {
                return Mono.error(buildException(HttpStatus.BAD_REQUEST));
            } else if (chatId == 404) {
                return Mono.error(buildException(HttpStatus.NOT_FOUND));
            } else {
                throw new AssertionError("Unreachable");
            }
        });
        return api;
    }
}
