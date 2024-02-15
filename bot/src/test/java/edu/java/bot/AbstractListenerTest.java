package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.annotations.BotCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AbstractListenerTest {

    protected TelegramBot bot = TestUtils.mockBot();

    @Test
    void testHelpMessage() {
        UpdateNotifierListener listener = new UpdateNotifierListener(bot);
        String s = listener.onHelp(null);

        List<String> lines = s.lines().toList();
        Set<String> linesUnique = new HashSet<>(lines);

        Assertions.assertEquals(5, lines.size());
        Assertions.assertEquals(lines.size(), linesUnique.size());

        Assertions.assertTrue(linesUnique.contains("/help - Возвращает список доступных команд"));
        Assertions.assertTrue(linesUnique.contains("/untrack - Прекратить отслеживание содержимого по ссылке"));
        Assertions.assertTrue(linesUnique.contains("/start - Зарегистрироваться в боте"));
        Assertions.assertTrue(linesUnique.contains("/track - Начать отслеживание содержимого по ссылке"));
        Assertions.assertTrue(linesUnique.contains("/list - Возвращает список отслеживаемых вами ссылок"));
    }

    @Test
    void testHelpEmptyDescription() {
        TestListener listener = new TestListener(bot);
        String s = listener.onHelp(null);

        List<String> lines = s.lines().toList();
        Set<String> linesUnique = new HashSet<>(lines);

        Assertions.assertEquals(2, lines.size());
        Assertions.assertEquals(lines.size(), linesUnique.size());

        Assertions.assertTrue(linesUnique.contains("/help - Возвращает список доступных команд"));
        Assertions.assertTrue(linesUnique.contains("/ping"));
    }

    private static class TestListener extends AbstractListener {
        public TestListener(TelegramBot bot) {
            super(bot);
        }

        @BotCommand("ping")
        public String onPing(Update update) {
            return "onPing";
        }
    }
}
