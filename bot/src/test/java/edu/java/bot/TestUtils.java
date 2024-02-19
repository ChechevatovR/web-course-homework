package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.Random;

public class TestUtils {
    public static final Random R = new Random(41);

    public static Update mockMessage(String text) {
        Chat chatMock = Mockito.mock(Chat.class);
        Mockito.when(chatMock.id()).thenReturn(R.nextLong());

        Message messageMock = Mockito.mock(Message.class);
        Mockito.when(messageMock.chat()).thenReturn(chatMock);
        Mockito.when(messageMock.text()).thenReturn(text);

        Update updateMock = Mockito.mock(Update.class);
        Mockito.when(updateMock.message()).thenReturn(messageMock);

        return updateMock;
    }

    public static TelegramBot mockBot() {
        TelegramBot mock = Mockito.mock(TelegramBot.class);
        return mock;
    }

    public static UpdateNotifierListener mockUpdateNotifierListener() {
        TelegramBot bot = mockBot();
        UpdateNotifierListener listener = new UpdateNotifierListener(bot);

        ArgumentCaptor<SetMyCommands> captor = ArgumentCaptor.forClass(SetMyCommands.class);
        Mockito.verify(bot).setUpdatesListener(listener);
        Mockito.verify(bot).execute(captor.capture());

        return listener;
    }
}
