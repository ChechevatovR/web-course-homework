package edu.java.scrapper.domain;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.domain.jdbc.JdbcChatsRepository;
import edu.java.scrapper.domain.model.Chat;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractChatsRepositoryTest extends IntegrationTest {

    protected final ChatsRepository repository;

    public AbstractChatsRepositoryTest(ChatsRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE chats, tracking");
        jdbcTemplate.execute("INSERT INTO chats VALUES (1, 11), (2, 22), (3, 33)");
        jdbcTemplate.execute("ALTER SEQUENCE chats_id_seq RESTART WITH 4");
    }

    @Test
    void testAddGeneratedId() {
        Chat chat = new Chat(44);
        repository.add(chat);
        Assertions.assertNotNull(chat.getId());
        Assertions.assertEquals(4, chat.getId());
        Chat chatFetched = jdbcTemplate.queryForObject(
            "SELECT * FROM chats WHERE id = ?",
            JdbcChatsRepository.ChatRowMapper.INSTANCE,
            chat.getId()
        );
        Assertions.assertNotNull(chatFetched);
        Assertions.assertEquals(chat.getId(), chatFetched.getId());
        Assertions.assertEquals(chat.getTelegramChatId(), chatFetched.getTelegramChatId());
    }

    @Test
    void testAddProvidedId() {
        Chat chat = new Chat(4, 44);
        repository.add(chat);
        Assertions.assertNotNull(chat.getId());
        Assertions.assertEquals(4, chat.getId());
        Chat chatFetched = jdbcTemplate.queryForObject(
            "SELECT * FROM chats WHERE id = ?",
            JdbcChatsRepository.ChatRowMapper.INSTANCE,
            chat.getId()
        );
        Assertions.assertNotNull(chatFetched);
        Assertions.assertEquals(chat.getId(), chatFetched.getId());
        Assertions.assertEquals(chat.getTelegramChatId(), chatFetched.getTelegramChatId());
    }

    @Test
    void testRemoveExisting() {
        boolean removed = repository.remove(1);
        Assertions.assertTrue(removed);
    }

    @Test
    void testRemoveNotExisting() {
        boolean removed = repository.remove(123);
        Assertions.assertFalse(removed);
    }

    @Test
    void testFindAll() {
        List<Chat> chats = repository.findAll().stream().toList();
        Assertions.assertEquals(3, chats.size());
        for (int i = 1; i <= 3; i++) {
            Chat chat = chats.get(i - 1);
            Assertions.assertEquals(i, chat.getId());
            Assertions.assertEquals(i + 10 * i, chat.getTelegramChatId());
        }
    }

    @Test
    void testFindById() {
        Chat chat = repository.findById(1);
        Assertions.assertNotNull(chat);
        Assertions.assertEquals(1, chat.getId());
        Assertions.assertEquals(11, chat.getTelegramChatId());
    }

    @Test
    void testFindByIdNull() {
        Chat chat = repository.findById(4);
        Assertions.assertNull(chat);
    }

    @Test
    void testFindByTgId() {
        Chat chat = repository.findByTelegramId(11L);
        Assertions.assertNotNull(chat);
        Assertions.assertEquals(1, chat.getId());
        Assertions.assertEquals(11, chat.getTelegramChatId());
    }

    @Test
    void testFindByTgIdNull() {
        Chat chat = repository.findById(44);
        Assertions.assertNull(chat);
    }
}
