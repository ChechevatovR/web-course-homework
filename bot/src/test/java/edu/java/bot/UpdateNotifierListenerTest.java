package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.exceptions.BadCommandUsage;
import edu.java.bot.exceptions.UnknownCommand;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class UpdateNotifierListenerTest {
    protected UpdateNotifierListener listener = TestUtils.mockUpdateNotifierListener();
    protected TelegramBot bot = listener.bot;

    @Test
    void testSetMyCommands() {
        TelegramBot mockBot = TestUtils.mockBot();
        UpdateNotifierListener mockListener = new UpdateNotifierListener(mockBot, null);

        ArgumentCaptor<SetMyCommands> captor = ArgumentCaptor.forClass(SetMyCommands.class);
        Mockito.verify(mockBot).setUpdatesListener(mockListener);
        Mockito.verify(mockBot).execute(captor.capture());

        SetMyCommands setMyCommands = captor.getValue();
        BotCommand[] commands = (BotCommand[]) setMyCommands.getParameters().get("commands");

        Assertions.assertEquals(5, commands.length);
    }

    @Test
    void testOnStartOkNew() {
        String response = listener.onStart(TestUtils.mockMessage(200, "/start"));
        Assertions.assertEquals("Успешная регистрация", response);
    }

    @Test
    void testOnStartOkExisting() {
        String response = listener.onStart(TestUtils.mockMessage(202, "/start"));
        Assertions.assertEquals("Вы уже были зарегистрированы", response);
    }

    @Test
    void testOnStartErrorDownstream() {
        Assertions.assertThrows(
            WebClientResponseException.BadRequest.class,
            () -> listener.onList(TestUtils.mockMessage(400, "/start"))
        );
    }

    @Test
    void testOnTrackOkNew() {
        String response = listener.onTrack(TestUtils.mockMessage(200, "/track https://github.com/shd/logic2023"));
        Assertions.assertEquals("Ссылка добавлена", response);
    }

    @Test
    void testOnTrackOkExisting() {
        String response = listener.onTrack(TestUtils.mockMessage(202, "/track https://github.com/shd/logic2023"));
        Assertions.assertEquals("Вы уже отслеживаете эту ссылку", response);
    }

    @Test
    void testOnTrackErrorNoUrl() {
        BadCommandUsage e = Assertions.assertThrows(
            BadCommandUsage.class,
            () -> listener.onTrack(TestUtils.mockMessage("/track"))
        );
        Assertions.assertEquals("URL не найден в сообщении", e.getMessage());
    }

    @Test
    void testOnTrackErrorDownstream() {
        Assertions.assertThrows(
            WebClientResponseException.BadRequest.class,
            () -> listener.onList(TestUtils.mockMessage(400, "/track https://github.com/shd/logic2023"))
        );
    }

    @Test
    void testOnUntrackOkRemoved() {
        String response = listener.onUntrack(TestUtils.mockMessage(200, "/untrack https://github.com/shd/logic2023"));
        Assertions.assertEquals("Ссылка удалена", response);
    }

    @Test
    void testOnUntrackOkNotTracked() {
        String response = listener.onUntrack(TestUtils.mockMessage(202, "/untrack https://github.com/shd/logic2023"));
        Assertions.assertEquals("Вы не отслеживаете эту ссылку", response);
    }

    @Test
    void testOnUntrackErrorNoUrl() {
        BadCommandUsage e = Assertions.assertThrows(
            BadCommandUsage.class,
            () -> listener.onUntrack(TestUtils.mockMessage("/untrack"))
        );
        Assertions.assertEquals("URL не найден в сообщении", e.getMessage());
    }

    @Test
    void testOnUntrackErrorDownstream() {
        Assertions.assertThrows(
            WebClientResponseException.BadRequest.class,
            () -> listener.onUntrack(TestUtils.mockMessage(400, "/untrack https://github.com/shd/logic2023"))
        );
    }

    @Test
    void testOnListEmpty() {
        String response = listener.onList(TestUtils.mockMessage(200, "/list"));
        Assertions.assertEquals("Список пуст", response);
    }

    @Test
    void testOnListNotEmpty() {
        String response = listener.onList(TestUtils.mockMessage(203, "/list"));
        List<String> lines = response.lines().toList();
        Assertions.assertEquals(
            List.of(
                "- https://example.com/0",
                "- https://example.com/1",
                "- https://example.com/2"
            ),
            lines
        );
    }

    @Test
    void testOnListErrorDownstream() {
        Assertions.assertThrows(
            WebClientResponseException.BadRequest.class,
            () -> listener.onList(TestUtils.mockMessage(400, "/list"))
        );
    }

    @Test
    void testOnUnknown() {
        Update update = TestUtils.mockMessage("/ping");
        listener.handleException(update, new UnknownCommand("ping"));

        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(bot).execute(captor.capture());

        String expected = """
                          Неизвестная команда: ping
                          Используйте /help для получения списка команд""";

        SendMessage capturedMessage = captor.getValue();

        Assertions.assertEquals(
            expected.lines().toList(),
            ((String) capturedMessage.getParameters().get("text")).lines().toList()
        );
        Assertions.assertEquals(ParseMode.Markdown.name(), capturedMessage.getParameters().get("parse_mode"));
    }
}
