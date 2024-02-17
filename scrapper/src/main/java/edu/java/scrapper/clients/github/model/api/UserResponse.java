package edu.java.scrapper.clients.github.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.clients.github.model.User;

public record UserResponse(
    long id,
    @JsonProperty("html_url")
    String url,
    String login
) {
    public User asModel() {
        return new User(id, url, login);
    }
}
