package edu.java.scrapper.domain.jpa.repositories.delegates;

import edu.java.scrapper.domain.jpa.entities.Chat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DelegateJpaChatsRepository extends
    JpaRepository<Chat, Integer> {
    Optional<Chat> findByTelegramChatId(long telegramChatId);
}
