package edu.java.scrapper.clients.github.model;

import java.net.URI;
import java.time.OffsetDateTime;

public record Repository(
    long id,
    URI uri,
    String name,
    String description,
    User owner,
    OffsetDateTime pushedAt,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
