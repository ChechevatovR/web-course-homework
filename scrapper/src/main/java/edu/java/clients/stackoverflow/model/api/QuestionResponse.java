package edu.java.clients.stackoverflow.model.api;

import edu.java.clients.stackoverflow.model.Question;
import java.time.OffsetDateTime;
import java.util.List;

public class QuestionResponse {
    public long accepted_answer_id;
    public List<AnswerResponse> answers = List.of();
    public String body;
    public OffsetDateTime closed_date;
    public List<CommentResponse> comments = List.of();
    public OffsetDateTime creation_date;
    public boolean is_answered;
    public OffsetDateTime last_activity_date;
    public String link;
    public UserResponse owner;
    public long question_id;
    public int score;
    public String title;

    public Question asModel() {
        return new Question(
            question_id,
            link,
            score,
            title,
            body,
            owner.asModel(),
            is_answered,
            answers.stream().map(AnswerResponse::asModel).toList(),
            closed_date,
            creation_date,
            last_activity_date,
            comments.stream().map(CommentResponse::asModel).toList()
        );
    }
}
