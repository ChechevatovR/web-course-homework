package edu.java.scrapper.clients.stackoverflow.model;

import java.time.OffsetDateTime;
import java.util.List;

public record Question(
    long id,
    String url,
    int score,
    String title,
//    String body,
    User owner,
    boolean isAnswered,
    List<Answer> answers,
    OffsetDateTime creationDate,
    OffsetDateTime lastActivityDate,
    List<Comment> comments
) {
}
