package edu.java.clients.github.model.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import edu.java.clients.github.model.Repository;
import java.time.OffsetDateTime;

public record RepositoryResponse(
    long id,
    String url,
    String name,
    String description,
    UserResponse owner,
    @JsonAlias("pushed_at")
    OffsetDateTime pushedAt,
    @JsonAlias("created_at")
    OffsetDateTime createdAt,
    @JsonAlias("updated_at")
    OffsetDateTime updatedAt
) {
    public Repository asModel() {
        return new Repository(
            id,
            url,
            name,
            description,
            owner.asModel(),
            pushedAt,
            createdAt,
            updatedAt
        );
    }
}
