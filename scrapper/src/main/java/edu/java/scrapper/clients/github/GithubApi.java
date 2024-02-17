package edu.java.scrapper.clients.github;

import edu.java.scrapper.clients.github.model.api.IssueResponse;
import edu.java.scrapper.clients.github.model.api.PullRequestResponse;
import edu.java.scrapper.clients.github.model.api.RepositoryResponse;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GithubApi {
    @GetExchange("/repos/{owner}/{repo}")
    RepositoryResponse getRepository(@PathVariable String owner, @PathVariable String repo);

    @GetExchange("repos/{owner}/{repo}/issues")
    List<IssueResponse> getIssuesForRepository(@PathVariable String owner, @PathVariable String repo);

    @GetExchange("/repos/{owner}/{repo}/issues/{issueNumber}")
    IssueResponse getIssue(@PathVariable String owner, @PathVariable String repo, @PathVariable int issueNumber);

    @GetExchange("repos/{owner}/{repo}/pulls")
    List<PullRequestResponse> getPullsForRepository(@PathVariable String owner, @PathVariable String repo);

    @GetExchange("/repos/{owner}/{repo}/pulls/{pullNumber}")
    PullRequestResponse getPull(@PathVariable String owner, @PathVariable String repo, @PathVariable int pullNumber);
}
