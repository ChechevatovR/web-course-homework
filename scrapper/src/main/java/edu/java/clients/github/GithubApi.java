package edu.java.clients.github;

import edu.java.clients.github.model.api.IssueResponse;
import edu.java.clients.github.model.api.PullRequestResponse;
import edu.java.clients.github.model.api.RepositoryResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import java.util.List;

public interface GithubApi {
    @GetExchange("/repos/{owner}/{repo}")
    RepositoryResponse getRepository(@PathVariable String owner, @PathVariable String repo);

    @GetExchange("repos/{owner}/{repo}/issues")
    List<IssueResponse> getIssuesForRepository(@PathVariable String owner, @PathVariable String repo);

    @GetExchange("/repos/{owner}/{repo}/issues/{issue_number}")
    IssueResponse getIssue(@PathVariable String owner, @PathVariable String repo, @PathVariable int issue_number);

    @GetExchange("repos/{owner}/{repo}/pulls")
    List<PullRequestResponse> getPullsForRepository(@PathVariable String owner, @PathVariable String repo);

    @GetExchange("/repos/{owner}/{repo}/pulls/{pull_number}")
    PullRequestResponse getPull(@PathVariable String owner, @PathVariable String repo, @PathVariable int pull_number);
}
