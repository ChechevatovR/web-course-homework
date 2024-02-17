package edu.java.scrapper.clients.stackoverflow.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.clients.stackoverflow.model.User;

public record UserResponse(
    @JsonProperty("accountId")
    long accountId,
    @JsonProperty("display_name")
    String displayName,
    String link,
    int reputation,
    @JsonProperty("userId")
    long userId
    ) {
    public User asModel() {
        return new User(userId, accountId, link, displayName, reputation);
    }
}
