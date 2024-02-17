package edu.java.clients.stackoverflow.model.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import edu.java.clients.stackoverflow.model.Comment;
import java.time.OffsetDateTime;

// https://api.stackexchange.com/docs/types/comment
public record CommentResponse(
    String body,
    @JsonAlias("comment_id")
    long id,
    @JsonAlias("creationDate")
    OffsetDateTime creationDate,
    String link,
    UserResponse owner,
    int score
) {
    public Comment asModel() {
        return new Comment(id, link, score, body, owner.asModel(), creationDate);
    }
}
