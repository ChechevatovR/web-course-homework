package edu.java.clients.stackoverflow.model.api;

import edu.java.clients.stackoverflow.model.Comment;
import java.time.OffsetDateTime;

// https://api.stackexchange.com/docs/types/comment
public record CommentResponse(
    String body,
    long comment_id,
    OffsetDateTime creation_date,
    String link,
    UserResponse owner,
//    long post_id,
    int score
)
{

    public Comment asModel() {
        return new Comment(comment_id, link, score, body, owner.asModel(), creation_date);
    }
}
