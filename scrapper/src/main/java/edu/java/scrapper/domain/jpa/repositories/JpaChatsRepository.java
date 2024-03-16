package edu.java.scrapper.domain.jpa.repositories;

import edu.java.scrapper.domain.ChatsRepository;
import edu.java.scrapper.domain.jpa.repositories.delegates.DelegateJpaChatsRepository;
import edu.java.scrapper.domain.model.Chat;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class JpaChatsRepository implements ChatsRepository {
    public final DelegateJpaChatsRepository delegate;

    public JpaChatsRepository(DelegateJpaChatsRepository delegate) {
        this.delegate = delegate;
    }

    public static Chat entityToDao(edu.java.scrapper.domain.jpa.entities.Chat chat) {
        return new Chat(chat.getId(), chat.getTelegramChatId());
    }

    public static edu.java.scrapper.domain.jpa.entities.Chat daoToEntity(Chat chat) {
        return new edu.java.scrapper.domain.jpa.entities.Chat(chat.getId(), chat.getTelegramChatId());
    }

    @Override
    public Chat add(Chat chat) {
        Chat result = entityToDao(delegate.save(daoToEntity(chat)));
        chat.setId(result.getId());
        delegate.flush();
        return result;
    }

    @Override
    @Transactional
    public boolean remove(int id) {
        boolean wasDeleted = delegate.existsById(id);
        delegate.deleteById(id);
        delegate.flush();
        return wasDeleted;
    }

    @Override
    public List<Chat> findAll() {
        return delegate.findAll().stream().map(JpaChatsRepository::entityToDao).toList();
    }

    @Override
    public Chat findById(int id) {
        return delegate.findById(id).map(JpaChatsRepository::entityToDao).orElse(null);
    }

    @Override
    public Chat findByTelegramId(long telegramChatId) {
        return delegate.findByTelegramChatId(telegramChatId).map(JpaChatsRepository::entityToDao).orElse(null);
    }
}
