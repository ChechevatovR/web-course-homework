package edu.java.clients.stackoverflow.model;

import java.time.OffsetDateTime;
import java.util.List;

public record Question (
    long id,
    String url,
    int score,
    String title,
    String body,
    User owner,
    boolean is_answered,
    List<Answer> answers,
    OffsetDateTime closed_date,
    OffsetDateTime creation_date,
    OffsetDateTime last_activity_date,
    List<Comment> comments
) {
}
