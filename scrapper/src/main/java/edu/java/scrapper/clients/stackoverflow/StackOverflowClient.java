package edu.java.scrapper.clients.stackoverflow;

import edu.java.scrapper.clients.stackoverflow.model.Answer;
import edu.java.scrapper.clients.stackoverflow.model.Comment;
import edu.java.scrapper.clients.stackoverflow.model.Question;
import edu.java.scrapper.clients.stackoverflow.model.api.AnswerResponse;
import edu.java.scrapper.clients.stackoverflow.model.api.CommentResponse;
import java.util.List;

public class StackOverflowClient {
    protected final StackOverflowApi api;

    public StackOverflowClient(StackOverflowApi api) {
        this.api = api;
    }

    public Question getQuestion(int id) {
        return api.getQuestion(id).items().get(0).asModel();
    }

    public List<Answer> getAnswersForQuestion(int id) {
        return api.getAnswersForQuestion(id).items().stream().map(AnswerResponse::asModel).toList();
    }

    public List<Comment> getCommentsForPost(int id) {
        return api.getCommentsForPost(id).items().stream().map(CommentResponse::asModel).toList();
    }
}
