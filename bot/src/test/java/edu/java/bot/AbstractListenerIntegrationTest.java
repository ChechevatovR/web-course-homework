package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.annotations.BotCommand;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class AbstractListenerIntegrationTest {

    protected UpdateNotifierListener listener = TestUtils.mockUpdateNotifierListener();
    protected TelegramBot bot = listener.bot;

    @Test
    void testMethodDispatchSuccess() {
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);

        Update update = TestUtils.mockMessage("/track");

        listener.process(List.of(update));

        Mockito.verify(bot).execute(captor.capture());

        SendMessage captured = captor.getValue();

        Assertions.assertEquals("onTrack", captured.getParameters().get("text"));
        Assertions.assertEquals(ParseMode.Markdown.name(), captured.getParameters().get("parse_mode"));
    }

    @Test
    void testMethodDispatchManySuccess() {
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);

        List<Update> updates = IntStream.range(0, 16)
                .mapToObj(i -> "/untrack " + i)
                .map(TestUtils::mockMessage)
                .toList();

        int processed = listener.process(updates);
        Assertions.assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, processed);

        Mockito.verify(bot, Mockito.times(16)).execute(captor.capture());

        List<SendMessage> captured = captor.getAllValues();
        for (int i = 0; i < 16; i++) {
            SendMessage capturedMsg = captured.get(i);

            Assertions.assertEquals("onUntrack", capturedMsg.getParameters().get("text"));
            Assertions.assertEquals(ParseMode.Markdown.name(), capturedMsg.getParameters().get("parse_mode"));
        }
    }

    @Test
    void testMethodDispatchFailNoCommand() {
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);

        Update update = TestUtils.mockMessage("Banana");

        listener.process(List.of(update));

        Mockito.verify(bot).execute(captor.capture());

        SendMessage captured = captor.getValue();

        Assertions.assertEquals("В сообщении не найдено команды", captured.getParameters().get("text"));
        Assertions.assertEquals(ParseMode.Markdown.name(), captured.getParameters().get("parse_mode"));
    }

    @Test
    void testMethodDispatchFailUnknownCommand() {
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);

        Update update = TestUtils.mockMessage("/ping");

        listener.process(List.of(update));

        Mockito.verify(bot).execute(captor.capture());

        SendMessage captured = captor.getValue();

        String expected = """
                          Неизвестная команда: ping
                          Используйте /help для получения списка команд""";

        Assertions.assertEquals(
            expected.lines().toList(),
            ((String) captured.getParameters().get("text")).lines().toList()
        );
        Assertions.assertEquals(ParseMode.Markdown.name(), captured.getParameters().get("parse_mode"));
    }

    @Test
    void testUnknownUpdate() {
        Update update = Mockito.mock(Update.class);
        Assertions.assertDoesNotThrow(
            () -> listener.process(List.of(update))
        );
    }

    private static class TestListener extends AbstractListener {
        public TestListener(TelegramBot bot) {
            super(bot);
        }

        @BotCommand("ping")
        public String onPing(Update update) {
            return "onPing: " + update.message().text();
        }
    }
}
