package edu.java.clients.stackoverflow;

import edu.java.clients.stackoverflow.model.Answer;
import edu.java.clients.stackoverflow.model.Comment;
import edu.java.clients.stackoverflow.model.Question;
import edu.java.clients.stackoverflow.model.api.AnswerResponse;
import edu.java.clients.stackoverflow.model.api.CommentResponse;
import java.util.List;

public class StackOverflowClient {
    protected final StackOverflowApi api;

    public StackOverflowClient(StackOverflowApi api) {
        this.api = api;
    }

    Question getQuestion(int id) {
        return api.getQuestion(id).items().get(0).asModel();
    }

    List<Answer> getAnswersForQuestion( int id) {
        return api.getAnswersForQuestion(id).items().stream().map(AnswerResponse::asModel).toList();
    }

    List<Comment> getCommentsForPost( int id) {
        return api.getCommentsForPost(id).items().stream().map(CommentResponse::asModel).toList();
    }
}
