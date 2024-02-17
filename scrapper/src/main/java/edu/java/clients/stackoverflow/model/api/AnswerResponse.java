package edu.java.clients.stackoverflow.model.api;

import edu.java.clients.stackoverflow.model.Answer;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

// https://api.stackexchange.com/docs/types/answer
public class AnswerResponse {
    public long answer_id;
    public String body;

    public List<CommentResponse> comments = List.of();

    public OffsetDateTime creation_date;
    public OffsetDateTime last_activity_date;
    public String link;
    public UserResponse owner;
    public long question_id;
    public int score;
    public String title;

//    public void setComments(List<CommentResponse> comments) {
//        this.comments = Objects.requireNonNullElseGet(comments, List::of);
//    }

    public Answer asModel() {
        return new Answer(
            answer_id,
            link,
            score,
            title,
            body,
            comments.stream().map(CommentResponse::asModel).toList(),
            creation_date,
            owner.asModel(),
            last_activity_date
        );
    }
}
