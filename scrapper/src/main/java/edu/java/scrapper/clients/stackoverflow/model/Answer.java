package edu.java.scrapper.clients.stackoverflow.model;

import java.time.OffsetDateTime;
import java.util.List;

// https://api.stackexchange.com/docs/types/answer
public record Answer(
    long id,
    String url,
    int score,
    String title,
//    String body,
    List<Comment> comments,
    OffsetDateTime creationDate,
    User owner,
    OffsetDateTime lastActivityDate
) {
}