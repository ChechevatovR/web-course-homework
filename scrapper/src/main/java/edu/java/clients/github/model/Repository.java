package edu.java.clients.github.model;

import java.time.OffsetDateTime;

public record Repository(
    long id,
    String url,
    String name,
    String description,
    User owner,
    OffsetDateTime pushed_at,
    OffsetDateTime created_at,
    OffsetDateTime updated_at
) {
}
