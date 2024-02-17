package edu.java.scrapper.clients.github.model;

import java.time.OffsetDateTime;

public record Repository(
    long id,
    String url,
    String name,
    String description,
    User owner,
    OffsetDateTime pushedAt,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
