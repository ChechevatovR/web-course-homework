package edu.java.clients.github.model.api;

import edu.java.clients.github.model.User;

public record UserResponse(
    long id,
    String url,
    String login
) {
    public User asModel() {
        return new User(id, url, login);
    }
}
