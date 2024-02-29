package edu.java.scrapper.clients.github.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.clients.github.model.Repository;
import java.net.URI;
import java.time.OffsetDateTime;

public record RepositoryResponse(
    long id,
    @JsonProperty("html_url")
    String url,
    String name,
    String description,
    UserResponse owner,
    @JsonProperty("pushed_at")
    OffsetDateTime pushedAt,
    @JsonProperty("created_at")
    OffsetDateTime createdAt,
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {
    public Repository asModel() {
        return new Repository(
            id,
            URI.create(url),
            name,
            description,
            owner.asModel(),
            pushedAt,
            createdAt,
            updatedAt
        );
    }
}
