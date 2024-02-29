package edu.java.scrapper.clients.stackoverflow.model;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public record Question(
    long id,
    URI uri,
    int score,
    String title,
    User owner,
    boolean isAnswered,
    List<Answer> answers,
    OffsetDateTime creationDate,
    OffsetDateTime lastActivityDate,
    List<Comment> comments
) {
}
