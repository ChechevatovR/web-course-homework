package edu.java.scrapper.clients.stackoverflow.model;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

// https://api.stackexchange.com/docs/types/answer
public record Answer(
    long id,
    URI uri,
    int score,
    String title,
    List<Comment> comments,
    OffsetDateTime creationDate,
    User owner,
    OffsetDateTime lastActivityDate
) {
}
