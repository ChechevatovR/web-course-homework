package edu.java.scrapper.clients.stackoverflow.model;

import java.net.URI;

public record User(
    long id,
    long accountId,
    URI uri,
    String displayName,
    int reputation
) {
}
