package edu.java.scrapper.clients.stackoverflow.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.clients.stackoverflow.model.Comment;
import java.net.URI;
import java.time.OffsetDateTime;

// https://api.stackexchange.com/docs/types/comment
public record CommentResponse(
    @JsonProperty("comment_id")
    long id,
    @JsonProperty("creation_date")
    OffsetDateTime creationDate,
    String link,
    UserResponse owner,
    int score
) {
    public Comment asModel() {
        return new Comment(
            id,
            URI.create(link),
            score,
            owner.asModel(),
            creationDate
        );
    }
}
