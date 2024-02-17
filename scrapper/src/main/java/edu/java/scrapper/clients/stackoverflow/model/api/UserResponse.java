package edu.java.scrapper.clients.stackoverflow.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.clients.stackoverflow.model.User;

public record UserResponse(
    long account_id,
    @JsonProperty("display_name")
    String displayName,
    String link,
    int reputation,
    long user_id
    ) {
    public User asModel() {
        return new User(user_id, account_id, link, displayName, reputation);
    }
}
