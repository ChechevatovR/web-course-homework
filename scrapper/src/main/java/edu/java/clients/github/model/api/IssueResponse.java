package edu.java.clients.github.model.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import edu.java.clients.github.model.Issue;
import java.time.OffsetDateTime;

public record IssueResponse(
    long id,
    String url,
    int number,
    String title,
    String body,
    UserResponse user,
    String state,
    boolean locked,
    @JsonAlias("created_at")
    OffsetDateTime createdAt,
    @JsonAlias("updated_at")
    OffsetDateTime updatedAt,
    @JsonAlias("closed_at")
    OffsetDateTime closedAt
) {
    public Issue asModel() {
        return new Issue(
            id,
            url,
            number,
            title,
            body,
            user.asModel(),
            state,
            locked,
            createdAt,
            updatedAt,
            closedAt
        );
    }
}
