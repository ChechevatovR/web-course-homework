package edu.java.clients.github.model.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import edu.java.clients.github.model.PullRequest;
import java.time.OffsetDateTime;

public record PullRequestResponse(
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
    OffsetDateTime closedAt,
    @JsonAlias("merged_at")
    OffsetDateTime mergedAt
) {
    public PullRequest asModel() {
        return new PullRequest(
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
            closedAt,
            mergedAt
        );
    }
}
