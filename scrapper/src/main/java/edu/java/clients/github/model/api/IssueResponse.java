package edu.java.clients.github.model.api;

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
    OffsetDateTime created_at,
    OffsetDateTime updated_at,
    OffsetDateTime closed_at
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
            created_at,
            updated_at,
            closed_at
        );
    }
}
