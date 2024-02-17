package edu.java.scrapper.clients.github.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.clients.github.model.PullRequest;
import java.time.OffsetDateTime;

public record PullRequestResponse(
    long id,
    @JsonProperty("html_url")
    String url,
    int number,
    String title,
//    String body,
    UserResponse user,
    String state,
    boolean locked,
    @JsonProperty("created_at")
    OffsetDateTime createdAt,
    @JsonProperty("updated_at")
    OffsetDateTime updatedAt,
    @JsonProperty("closed_at")
    OffsetDateTime closedAt,
    @JsonProperty("merged_at")
    OffsetDateTime mergedAt
) {
    public PullRequest asModel() {
        return new PullRequest(
            id,
            url,
            number,
            title,
//            body,
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
