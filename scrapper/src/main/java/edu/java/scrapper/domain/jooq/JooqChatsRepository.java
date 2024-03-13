package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.ChatsRepository;
import edu.java.scrapper.domain.jooq.tables.Chats;
import edu.java.scrapper.domain.model.Chat;
import java.util.List;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;

public class JooqChatsRepository implements ChatsRepository {
    public static final Chats CHATS = Chats.CHATS;
    public final DSLContext dslContext;

    public JooqChatsRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public JooqChatsRepository(DataSource dataSource) {
        this(new DefaultDSLContext(dataSource, SQLDialect.POSTGRES));
    }

    @Override
    public Chat add(Chat chat) {
        if (chat.getId() == null) {
            Integer id = dslContext.insertInto(CHATS, CHATS.TELEGRAM_CHAT_ID)
                .values(chat.getTelegramChatId())
                .returningResult(CHATS.ID)
                .fetchSingle(CHATS.ID);
            chat.setId(id);
        } else {
            dslContext.insertInto(CHATS, CHATS.ID, CHATS.TELEGRAM_CHAT_ID)
                .values(chat.getId(), chat.getTelegramChatId())
                .execute();
        }
        return chat;
    }

    @Override
    public boolean remove(int id) {
        int affectedRows = dslContext.deleteFrom(CHATS)
            .where(CHATS.ID.eq(id))
            .execute();
        return affectedRows >= 1;
    }

    @Override
    public List<Chat> findAll() {
        return dslContext.select(CHATS.asterisk())
            .from(CHATS)
            .fetchInto(Chat.class);
    }

    @Override
    public Chat findById(int id) {
        return dslContext.select(CHATS.asterisk())
            .from(CHATS)
            .where(CHATS.ID.eq(id))
            .fetchOneInto(Chat.class);
    }

    @Override
    public Chat findByTelegramId(long telegramChatId) {
        return dslContext.select(CHATS.asterisk())
            .from(CHATS)
            .where(CHATS.TELEGRAM_CHAT_ID.eq(telegramChatId))
            .fetchOneInto(Chat.class);
    }
}
