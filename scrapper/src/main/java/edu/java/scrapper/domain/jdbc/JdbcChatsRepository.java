package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.ChatsRepository;
import edu.java.scrapper.domain.model.Chat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcChatsRepository implements ChatsRepository {
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcChatsRepository(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(jdbc)
            .withTableName("chats")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Chat add(Chat chat) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("telegram_chat_id", chat.getTelegramChatId());
        if (chat.getId() == null) {
            Number id = jdbcInsert.executeAndReturnKey(parameterSource);
            chat.setId(id.intValue());
        } else {
            parameterSource.addValue("id", chat.getId());
            jdbcInsert.execute(parameterSource);
        }
        return chat;
    }

    @Override
    public boolean remove(int id) {
        int affectedRows = jdbc.update("DELETE from chats WHERE id = ?", id);
        return affectedRows >= 1;
    }

    @Override
    public List<Chat> findAll() {
        return jdbc.query("SELECT * FROM chats", ChatRowMapper.INSTANCE);
    }

    @Override
    public Chat findById(int id) {
        try {
            return jdbc.queryForObject(
                "SELECT * FROM chats WHERE id = ?",
                ChatRowMapper.INSTANCE,
                id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Chat findByTelegramId(long telegramChatId) {
        try {
            return jdbc.queryForObject(
                "SELECT * FROM chats WHERE telegram_chat_id = ?",
                ChatRowMapper.INSTANCE,
                telegramChatId
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public static class ChatRowMapper implements RowMapper<Chat> {
        public static final ChatRowMapper INSTANCE = new ChatRowMapper();

        private ChatRowMapper() {
        }

        @Override
        public Chat mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Chat(rs.getInt(1), rs.getInt(2));
        }
    }
}
