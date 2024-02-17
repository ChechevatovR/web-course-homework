package edu.java.clients.stackoverflow.model.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import edu.java.clients.stackoverflow.model.Answer;
import java.time.OffsetDateTime;
import java.util.List;

// https://api.stackexchange.com/docs/types/answer
public class AnswerResponse {
    @JsonAlias("answer_id")
    public long id;
    public String body;

    public List<CommentResponse> comments = List.of();

    @JsonAlias("creation_date")
    public OffsetDateTime creationDate;
    @JsonAlias("lastActivityDate")
    public OffsetDateTime lastActivityDate;
    public String link;
    public UserResponse owner;
    @JsonAlias("question_id")
    public long questionId;
    public int score;
    public String title;

    public Answer asModel() {
        return new Answer(
            id,
            link,
            score,
            title,
            body,
            comments.stream().map(CommentResponse::asModel).toList(),
            creationDate,
            owner.asModel(),
            lastActivityDate
        );
    }
}
