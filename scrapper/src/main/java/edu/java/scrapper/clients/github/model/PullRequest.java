package edu.java.scrapper.clients.github.model;

import java.time.OffsetDateTime;

public record PullRequest(
    long id,
    String url,
    int number,
    String title,
//    String body,
    User user,
    String state,
    boolean locked,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    OffsetDateTime closedAt,
    OffsetDateTime mergedAt
    ) {
}
