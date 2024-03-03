package edu.java.scrapper.configuration;

public class Clients {
    BotClient bot = new BotClient();
    GithubClient github = new GithubClient();
    StackOverflowClient stackoverflow = new StackOverflowClient();

    public GithubClient getGithub() {
        return github;
    }

    void setGithub(GithubClient github) {
        this.github = github;
    }

    public StackOverflowClient getStackoverflow() {
        return stackoverflow;
    }

    void setStackoverflow(StackOverflowClient stackoverflow) {
        this.stackoverflow = stackoverflow;
    }

    public BotClient getBot() {
        return bot;
    }

    void setBot(BotClient bot) {
        this.bot = bot;
    }
}
