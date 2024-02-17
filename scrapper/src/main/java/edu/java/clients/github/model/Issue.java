package edu.java.clients.github.model;

import java.time.OffsetDateTime;

public record Issue(
    long id,
    String url,
    int number,
    String title,
    String body,
    User user,
    String state,
    boolean locked,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    OffsetDateTime closedAt
) {
}
