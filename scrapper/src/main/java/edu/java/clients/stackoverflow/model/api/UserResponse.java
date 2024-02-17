package edu.java.clients.stackoverflow.model.api;

import edu.java.clients.stackoverflow.model.User;

public record UserResponse(
    long user_id,
    String link,
    String display_name,
    int reputation
) {
    public User asModel() {
        return new User(user_id, link, display_name, reputation);
    }
}
