package edu.java.clients.github.model.api;

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
    OffsetDateTime created_at,
    OffsetDateTime updated_at,
    OffsetDateTime closed_at,
    OffsetDateTime merged_at
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
            created_at,
            updated_at,
            closed_at,
            merged_at
        );
    }
}
