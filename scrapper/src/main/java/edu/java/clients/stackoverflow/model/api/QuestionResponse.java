package edu.java.clients.stackoverflow.model.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import edu.java.clients.stackoverflow.model.Question;
import java.time.OffsetDateTime;
import java.util.List;

public class QuestionResponse {
    @JsonAlias("accepted_answer_id")
    public long acceptedAnswerId;
    public List<AnswerResponse> answers = List.of();
    public String body;
    @JsonAlias("closed_date")
    public OffsetDateTime closedDate;
    public List<CommentResponse> comments = List.of();
    @JsonAlias("creationDate")
    public OffsetDateTime creationDate;
    @JsonAlias("isAnswered")
    public boolean isAnswered;
    @JsonAlias("lastActivityDate")
    public OffsetDateTime lastActivityDate;
    public String link;
    public UserResponse owner;
    @JsonAlias("question_id")
    public long questionId;
    public int score;
    public String title;

    public Question asModel() {
        return new Question(
            questionId,
            link,
            score,
            title,
            body,
            owner.asModel(),
            isAnswered,
            answers.stream().map(AnswerResponse::asModel).toList(),
            closedDate,
            creationDate,
            lastActivityDate,
            comments.stream().map(CommentResponse::asModel).toList()
        );
    }
}
