package edu.java.scrapper.clients.stackoverflow.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.clients.stackoverflow.model.Question;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public class QuestionResponse {
    @JsonProperty("accepted_answer_id")
    public long acceptedAnswerId;
    public List<AnswerResponse> answers = List.of();
    public List<CommentResponse> comments = List.of();
    @JsonProperty("creation_date")
    public OffsetDateTime creationDate;
    @JsonProperty("is_answered")
    public boolean isAnswered;
    @JsonProperty("last_activity_date")
    public OffsetDateTime lastActivityDate;
    public String link;
    public UserResponse owner;
    @JsonProperty("question_id")
    public long questionId;
    public int score;
    public String title;

    public Question asModel() {
        List<AnswerResponse> answersSafe = Objects.requireNonNullElse(answers, List.of());
        List<CommentResponse> commentsSafe = Objects.requireNonNullElse(comments, List.of());
        return new Question(
            questionId,
            URI.create(link),
            score,
            title,
            owner.asModel(),
            isAnswered,
            answersSafe.stream().map(AnswerResponse::asModel).toList(),
            creationDate,
            lastActivityDate,
            commentsSafe.stream().map(CommentResponse::asModel).toList()
        );
    }
}
