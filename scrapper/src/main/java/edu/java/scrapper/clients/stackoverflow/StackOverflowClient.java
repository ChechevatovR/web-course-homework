package edu.java.scrapper.clients.stackoverflow;

import edu.java.scrapper.clients.stackoverflow.model.Question;

public class StackOverflowClient {
    protected final StackOverflowApi api;

    public StackOverflowClient(StackOverflowApi api) {
        this.api = api;
    }

    public Question getQuestion(long id) {
        return api.getQuestion(id).items().get(0).asModel();
    }
}
