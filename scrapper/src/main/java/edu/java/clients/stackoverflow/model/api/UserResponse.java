package edu.java.clients.stackoverflow.model.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import edu.java.clients.stackoverflow.model.User;

public record UserResponse(
    @JsonAlias("user_id")
    long id,
    String link,
    @JsonAlias("display_name")
    String displayName,
    int reputation
) {
    public User asModel() {
        return new User(id, link, displayName, reputation);
    }
}
