package edu.java.clients.github.model.api;

import edu.java.clients.github.model.Repository;
import java.time.OffsetDateTime;

public record RepositoryResponse(
    long id,
    String url,
    String name,
    String description,
    UserResponse owner,
    OffsetDateTime pushed_at,
    OffsetDateTime created_at,
    OffsetDateTime updated_at
) {
    public Repository asModel() {
        return new Repository(
            id, url, name, description, owner.asModel(), pushed_at, created_at, updated_at
        );
    }
}
