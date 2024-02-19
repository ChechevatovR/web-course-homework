package edu.java.scrapper.clients.github.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.clients.github.model.Issue;
import java.net.URI;
import java.time.OffsetDateTime;

public record IssueResponse(
    long id,
    @JsonProperty("html_url")
    String url,
    int number,
    String title,
    UserResponse user,
    String state,
    boolean locked,
    @JsonProperty("created_at")
    OffsetDateTime createdAt,
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt,
    @JsonProperty("closed_at")
    OffsetDateTime closedAt
) {
    public Issue asModel() {
        return new Issue(
            id,
            URI.create(url),
            number,
            title,
            user.asModel(),
            state,
            locked,
            createdAt,
            updatedAt,
            closedAt
        );
    }
}
