package edu.java.clients.stackoverflow.model;

import java.time.OffsetDateTime;

// https://api.stackexchange.com/docs/types/comment
public record Comment(
    long id,
    String url,
    int score,
    String body,
    User owner,
    OffsetDateTime creationDate
) {
}
