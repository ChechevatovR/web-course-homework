package edu.java.scrapper.domain.model;

import java.util.Objects;
import java.util.StringJoiner;

public class LinkTracking {
    private final int chatId;
    private final int linkId;

    public LinkTracking(int chatId, int linkId) {
        this.chatId = chatId;
        this.linkId = linkId;
    }

    public int getChatId() {
        return chatId;
    }

    public int getLinkId() {
        return linkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinkTracking tracking = (LinkTracking) o;
        return chatId == tracking.chatId && linkId == tracking.linkId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, linkId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LinkTracking.class.getSimpleName() + "[", "]")
            .add("chatId=" + chatId)
            .add("linkId=" + linkId)
            .toString();
    }
}
