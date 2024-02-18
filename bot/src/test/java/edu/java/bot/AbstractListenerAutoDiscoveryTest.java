package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.annotations.BotCommand;
import edu.java.bot.exceptions.MethodHandlerSignatureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractListenerAutoDiscoveryTest {
    protected TelegramBot bot = TestUtils.mockBot();

    @Test
    void testAutoMethodDiscoveryArgsNoFails() {
        Assertions.assertThrows(
            MethodHandlerSignatureException.class,
            () -> new TestListenerNoArgs(bot)
        );
    }

    @Test
    void testAutoMethodDiscoveryArgsExtraFails() {
        Assertions.assertThrows(
            MethodHandlerSignatureException.class,
            () -> new TestListenerExtraArgs(bot)
        );
    }

    @Test
    void testAutoMethodDiscoveryArgsUnsupportedFails() {
        Assertions.assertThrows(
            MethodHandlerSignatureException.class,
            () -> new TestListenerUnsupportedArgs(bot)
        );
    }

    @Test
    void testAutoMethodDiscoveryReturnStringWorks() {
        AbstractListener listener = new TestListenerReturnString(bot);
        Assertions.assertEquals(2, listener.commands.size());
    }

    @Test
    void testAutoMethodDiscoveryReturnVoidWorks() {
        AbstractListener listener = new TestListenerReturnVoid(bot);
        Assertions.assertEquals(2, listener.commands.size());
    }

    @Test
    void testAutoMethodDiscoveryReturnUnsupportedFails() {
        Assertions.assertThrows(
            MethodHandlerSignatureException.class,
            () -> new TestListenerReturnUnsupported(bot)
        );
    }

    @Test
    void testAutoMethodDiscoveryMultipleHandlersFails() {
        Assertions.assertThrows(
            MethodHandlerSignatureException.class,
            () -> new TestListenerMultipleHandlers(bot)
        );
    }

    ////////////////////////////////////////////////////////////////////////
    private static class TestListenerNoArgs extends AbstractListener {
        public TestListenerNoArgs(TelegramBot bot) {
            super(bot);
        }

        @BotCommand("ping")
        public String onPing() {
            return "onPing";
        }
    }

    private static class TestListenerExtraArgs extends AbstractListener {
        public TestListenerExtraArgs(TelegramBot bot) {
            super(bot);
        }

        @BotCommand("ping")
        public String onPing(Update update, String whom) {
            return "onPing";
        }
    }

    private static class TestListenerUnsupportedArgs extends AbstractListener {
        public TestListenerUnsupportedArgs(TelegramBot bot) {
            super(bot);
        }

        @BotCommand("ping")
        public String onPing(Message message) {
            return "onPing";
        }
    }

    private static class TestListenerReturnString extends AbstractListener {
        public TestListenerReturnString(TelegramBot bot) {
            super(bot);
        }

        @BotCommand("ping")
        public String onPing(Update update) {
            return "onPing";
        }
    }

    private static class TestListenerReturnVoid extends AbstractListener {
        public TestListenerReturnVoid(TelegramBot bot) {
            super(bot);
        }

        @BotCommand("ping")
        public void onPing(Update update) {
        }
    }

    private static class TestListenerReturnUnsupported extends AbstractListener {
        public TestListenerReturnUnsupported(TelegramBot bot) {
            super(bot);
        }

        @BotCommand("ping")
        public Integer onPing(Update update) {
            return 2;
        }
    }

    private static class TestListenerMultipleHandlers extends AbstractListener {
        public TestListenerMultipleHandlers(TelegramBot bot) {
            super(bot);
        }

        @BotCommand("ping")
        public void onPing(Update update) {
        }

        @BotCommand("ping")
        public void onPing2(Update update) {
        }
    }
}
