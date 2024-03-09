package edu.java.scrapper.service;

import edu.java.scrapper.domain.ChatsRepository;
import edu.java.scrapper.domain.model.Chat;
import edu.java.scrapper.domain.model.IsNewWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class ChatsService {

    @Autowired
    private ChatsRepository chatsRepository;

    public IsNewWrapper<Chat> register(long telegramChatId) {
        try {
            Chat newChat = new Chat(telegramChatId);
            chatsRepository.add(newChat);
            return new IsNewWrapper<>(true, newChat);
        } catch (DuplicateKeyException e) {
            Chat exisitngChat = chatsRepository.findByTelegramId(telegramChatId);
            return new IsNewWrapper<>(false, exisitngChat);
        }
    }

    public boolean deregister(long telegramChatId) {
        Chat chat = chatsRepository.findByTelegramId(telegramChatId);
        if (chat == null) {
            return false;
        }
        chatsRepository.remove(chat.getId());
        return true;
    }
}
