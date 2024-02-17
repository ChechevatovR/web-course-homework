package edu.java.clients.github;

import edu.java.clients.github.model.Issue;
import edu.java.clients.github.model.PullRequest;
import edu.java.clients.github.model.Repository;
import edu.java.clients.github.model.api.IssueResponse;
import edu.java.clients.github.model.api.PullRequestResponse;
import java.util.List;

public class GithubClient {
    protected final GithubApi api;

    public GithubClient(GithubApi api) {
        this.api = api;
    }

    public Repository getRepository(String owner, String repo) {
        return api.getRepository(owner, repo).asModel();
    }

    public List<Issue> getIssuesForRepository(String owner, String repo) {
        return api.getIssuesForRepository(owner, repo).stream().map(IssueResponse::asModel).toList();
    }

    public Issue getIssue(String owner, String repo, int issueNumber) {
        return api.getIssue(owner, repo, issueNumber).asModel();
    }

    public List<PullRequest> getPullsForRepository(String owner, String repo) {
        return api.getPullsForRepository(owner, repo).stream().map(PullRequestResponse::asModel).toList();
    }

    public PullRequest getPull(String owner, String repo, int pullNumber) {
        return api.getPull(owner, repo, pullNumber).asModel();
    }
}
