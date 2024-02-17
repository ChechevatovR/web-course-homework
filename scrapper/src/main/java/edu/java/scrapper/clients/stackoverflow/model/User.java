package edu.java.scrapper.clients.stackoverflow.model;

public record User(
    long id,
    long accountId,
    String url,
    String displayName,
    int reputation
) {
}
