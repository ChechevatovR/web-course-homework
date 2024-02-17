package edu.java.clients.stackoverflow.model;

import java.time.OffsetDateTime;
import java.util.List;

// https://api.stackexchange.com/docs/types/answer
public record Answer(
    long id,
    String url,
    int score,
    String title,
    String body,
    List<Comment> comments,
    OffsetDateTime creation_date,
    User owner,
    OffsetDateTime last_activity_date
) {
}
