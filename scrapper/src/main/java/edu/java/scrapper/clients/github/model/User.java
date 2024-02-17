package edu.java.scrapper.clients.github.model;

public record User(
    long id,
    String url,
    String login
) {
}
