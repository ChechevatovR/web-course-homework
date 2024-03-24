package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.annotations.BotCommand;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractListenerTest {

    protected TelegramBot bot = TestUtils.mockBot();

    @Test
    void testHelpMessage() {
        UpdateNotifierListener listener = new UpdateNotifierListener(bot, null);
        String s = listener.onHelp(null);

        List<String> lines = s.lines().toList();

        Assertions.assertEquals(5, lines.size());

        Assertions.assertEquals("/list - Возвращает список отслеживаемых вами ссылок", lines.get(0));
        Assertions.assertEquals("/track `<url>` - Начать отслеживание содержимого по ссылке", lines.get(1));
        Assertions.assertEquals("/untrack `<url>` - Прекратить отслеживание содержимого по ссылке", lines.get(2));
        Assertions.assertEquals("/start - Зарегистрироваться в боте", lines.get(3));
        Assertions.assertEquals("/help - Возвращает список доступных команд", lines.get(4));
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

    private static class TestListener extends AutoHelpListener {
        public TestListener(TelegramBot bot) {
            super(bot);
        }

        @BotCommand("ping")
        public String onPing(Update update) {
            return "onPing";
        }
    }
}
