package edu.java.clients.stackoverflow.model;

public record User(
    long id,
    String url,
    String displayName,
    int reputation
) {
}
