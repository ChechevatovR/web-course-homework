package edu.java.scrapper.clients.stackoverflow.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.clients.stackoverflow.model.Answer;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

// https://api.stackexchange.com/docs/types/answer
public class AnswerResponse {
    @JsonProperty("answer_id")
    public long id;
    public List<CommentResponse> comments = List.of();
    @JsonProperty("creation_date")
    public OffsetDateTime creationDate;
    @JsonProperty("last_activity_date")
    public OffsetDateTime lastActivityDate;
    public String link;
    public UserResponse owner;
    @JsonProperty("question_id")
    public long questionId;
    public int score;
    public String title;

    public Answer asModel() {
        return new Answer(
            id,
            URI.create(link),
            score,
            title,
            comments.stream().map(CommentResponse::asModel).toList(),
            creationDate,
            owner.asModel(),
            lastActivityDate
        );
    }
}
