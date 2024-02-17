package edu.java.scrapper.clients.stackoverflow.model;

public record User(
    long id,
    long account_id,
    String url,
    String displayName,
    int reputation
) {
}
