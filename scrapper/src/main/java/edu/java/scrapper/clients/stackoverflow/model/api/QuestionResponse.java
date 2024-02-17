package edu.java.scrapper.clients.stackoverflow.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.clients.stackoverflow.model.Question;
import java.time.OffsetDateTime;
import java.util.List;

public class QuestionResponse {
    @JsonProperty("accepted_answer_id")
    public long acceptedAnswerId;
    public List<AnswerResponse> answers = List.of();
//    public String body;
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
        return new Question(
            questionId,
            link,
            score,
            title,
//            body,
            owner.asModel(),
            isAnswered,
            answers.stream().map(AnswerResponse::asModel).toList(),
            creationDate,
            lastActivityDate,
            comments.stream().map(CommentResponse::asModel).toList()
        );
    }
}
