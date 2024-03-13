package edu.java.scrapper.domain;

import edu.java.scrapper.domain.model.Chat;
import java.util.Collection;

public interface ChatsRepository {
    Chat add(Chat chat);

    boolean remove(int id);

    Collection<Chat> findAll();

    Chat findById(int id);

    Chat findByTelegramId(long telegramChatId);
}
