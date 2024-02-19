package edu.java.scrapper.clients.github.model;

import java.net.URI;
import java.time.OffsetDateTime;

public record PullRequest(
    long id,
    URI uri,
    int number,
    String title,
    User user,
    String state,
    boolean locked,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    OffsetDateTime closedAt,
    OffsetDateTime mergedAt
    ) {
}
