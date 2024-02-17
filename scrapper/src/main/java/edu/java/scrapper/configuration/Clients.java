package edu.java.scrapper.configuration;

public class Clients {
    public GithubClient github = new GithubClient();
    public StackOverflowClient stackoverflow = new StackOverflowClient();

    public GithubClient getGithub() {
        return github;
    }

    public void setGithub(GithubClient github) {
        this.github = github;
    }

    public StackOverflowClient getStackoverflow() {
        return stackoverflow;
    }

    public void setStackoverflow(StackOverflowClient stackoverflow) {
        this.stackoverflow = stackoverflow;
    }
}
