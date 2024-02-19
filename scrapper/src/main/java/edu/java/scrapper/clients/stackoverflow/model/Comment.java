package edu.java.scrapper.clients.stackoverflow.model;

import java.net.URI;
import java.time.OffsetDateTime;

// https://api.stackexchange.com/docs/types/comment
public record Comment(
    long id,
    URI uri,
    int score,
    User owner,
    OffsetDateTime creationDate
) {
}
