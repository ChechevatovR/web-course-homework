package edu.java.scrapper.domain.model;

import java.util.StringJoiner;

public class Chat {
    private final long telegramChatId;
    private Integer id;

    public Chat(Integer id, long telegramChatId) {
        this.id = id;
        this.telegramChatId = telegramChatId;
    }

    public Chat(long telegramChatId) {
        this(null, telegramChatId);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getTelegramChatId() {
        return telegramChatId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Chat.class.getSimpleName() + "[", "]")
            .add("telegramChatId=" + telegramChatId)
            .add("id=" + id)
            .toString();
    }
}
