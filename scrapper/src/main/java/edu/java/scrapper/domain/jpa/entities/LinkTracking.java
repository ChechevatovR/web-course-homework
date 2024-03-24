package edu.java.scrapper.domain.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tracking")
@IdClass(LinkTracking.CompositeKey.class)
public class LinkTracking {
    @Id
    private int chatId;

    @Id
    private int linkId;

    public LinkTracking(int chatId, int linkId) {
        this.chatId = chatId;
        this.linkId = linkId;
    }

    public LinkTracking() {
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public static class CompositeKey implements Serializable {
        private int chatId;
        private int linkId;

        public CompositeKey() {
        }

        public CompositeKey(int chatId, int linkId) {
            this.chatId = chatId;
            this.linkId = linkId;
        }

        @Override public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CompositeKey that = (CompositeKey) o;
            return chatId == that.chatId && linkId == that.linkId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(chatId, linkId);
        }
    }
}
