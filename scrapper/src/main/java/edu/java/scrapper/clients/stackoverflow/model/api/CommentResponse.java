package edu.java.scrapper.clients.stackoverflow.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.clients.stackoverflow.model.Comment;
import java.time.OffsetDateTime;

// https://api.stackexchange.com/docs/types/comment
public record CommentResponse(
//    String body,
    @JsonProperty("comment_id")
    long id,
    @JsonProperty("creationDate")
    OffsetDateTime creationDate,
    String link,
    UserResponse owner,
    int score
) {
    public Comment asModel() {
        return new Comment(
            id,
            link,
            score,
//            body,
            owner.asModel(),
            creationDate
        );
    }
}
