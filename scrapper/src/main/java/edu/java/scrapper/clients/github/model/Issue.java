package edu.java.scrapper.clients.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record Issue(
    long id,
    @JsonProperty("html_url")
    String url,
    int number,
    String title,
//    String body,
    User user,
    String state,
    boolean locked,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    OffsetDateTime closedAt
) {
}
