package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.exceptions.UnknownCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class UpdateNotifierListenerTest {
    protected UpdateNotifierListener listener = TestUtils.mockUpdateNotifierListener();
    protected TelegramBot bot = listener.bot;

    @Test
    void testSetMyCommands() {
        TelegramBot mockBot = TestUtils.mockBot();
        UpdateNotifierListener mockListener = new UpdateNotifierListener(mockBot);

        ArgumentCaptor<SetMyCommands> captor = ArgumentCaptor.forClass(SetMyCommands.class);
        Mockito.verify(mockBot).setUpdatesListener(mockListener);
        Mockito.verify(mockBot).execute(captor.capture());

        SetMyCommands setMyCommands = captor.getValue();
        BotCommand[] commands = (BotCommand[]) setMyCommands.getParameters().get("commands");

        Assertions.assertEquals(5, commands.length);
    }

    @Test
    void testOnStart() {
        Assertions.assertEquals("onStart", listener.onStart(null));
    }

    @Test
    void testOnTrack() {
        Assertions.assertEquals("onTrack", listener.onTrack(null));
    }

    @Test
    void testOnUntrack() {
        Assertions.assertEquals("onUntrack", listener.onUntrack(null));
    }

    @Test
    void testOnList() {
        Assertions.assertEquals("onList", listener.onList(null));
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
        Assertions.assertEquals(ParseMode.Markdown.name(), capturedMessage.getParameters().get("parse_mode") );
    }
}
