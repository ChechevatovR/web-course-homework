package edu.java.clients.github.model;

import java.time.OffsetDateTime;

public record PullRequest(
    long id,
    String url,
    int number,
    String title,
    String body,
    User user,
    String state,
    boolean locked,
    OffsetDateTime created_at,
    OffsetDateTime updated_at,
    OffsetDateTime closed_at,
    OffsetDateTime merged_at
//    UserResponse assignee,
//    List<UserResponse> requestedReviewers
    ) {
}
