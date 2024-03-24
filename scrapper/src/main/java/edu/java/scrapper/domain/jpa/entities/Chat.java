package edu.java.scrapper.domain.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chats_id_seq")
    @SequenceGenerator(name = "chats_id_seq", sequenceName = "chats_id_seq", allocationSize = 1)
    private Integer id;

    private long telegramChatId;

    public Chat() {
    }

    public Chat(Integer id, long telegramChatId) {
        this.id = id;
        this.telegramChatId = telegramChatId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public long getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }
}
