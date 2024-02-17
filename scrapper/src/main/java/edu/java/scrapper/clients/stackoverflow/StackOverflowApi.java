package edu.java.scrapper.clients.stackoverflow;

import edu.java.scrapper.clients.stackoverflow.model.api.AnswerResponse;
import edu.java.scrapper.clients.stackoverflow.model.api.CommentResponse;
import edu.java.scrapper.clients.stackoverflow.model.api.QuestionResponse;
import edu.java.scrapper.clients.stackoverflow.model.api.Wrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface StackOverflowApi {
    @GetExchange("/questions/{id}")
    Wrapper<QuestionResponse> getQuestion(@PathVariable int id);

    @GetExchange("/questions/{id}/answers")
    Wrapper<AnswerResponse> getAnswersForQuestion(@PathVariable int id);

    @GetExchange("/posts/{id}/comments")
    Wrapper<CommentResponse> getCommentsForPost(@PathVariable int id);
}
