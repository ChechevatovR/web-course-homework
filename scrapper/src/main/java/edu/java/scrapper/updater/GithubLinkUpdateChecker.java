package edu.java.scrapper.updater;

import edu.java.scrapper.clients.github.GithubClient;
import edu.java.scrapper.clients.github.model.Issue;
import edu.java.scrapper.clients.github.model.PullRequest;
import edu.java.scrapper.clients.github.model.Repository;
import edu.java.scrapper.domain.LinksGithubRepository;
import edu.java.scrapper.domain.model.GithubLink;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.model.Site;
import edu.java.scrapper.exception.UnsupportedLink;
import org.springframework.stereotype.Component;

@Component
public class GithubLinkUpdateChecker implements LinkUpdateChecker {

    public final GithubClient githubClient;
    public final LinksGithubRepository linksGithubRepository;

    public GithubLinkUpdateChecker(GithubClient githubClient, LinksGithubRepository linksGithubRepository) {
        this.githubClient = githubClient;
        this.linksGithubRepository = linksGithubRepository;
    }

    @Override
    public boolean isLinkSupported(Link link) {
        if (link.getSite() != Site.GITHUB) {
            return false;
        }
        try {
            GithubLink.checkUrlSupported(link.getUrl());
            return true;
        } catch (UnsupportedLink e) {
            return false;
        }
    }

    @Override
    public boolean isUpdated(Link l) {
        GithubLink link = linksGithubRepository.findById(l.getId());
        String owner = link.getOwner();
        String repo = link.getRepo();
        Repository repository = githubClient.getRepository(
            owner,
            repo
        );
        Optional<Integer> latestIssue = githubClient.getIssuesForRepository(owner, repo).stream()
            .map(Issue::number)
            .max(Integer::compareTo);
        Optional<Integer> latestPR = githubClient.getPullsForRepository(owner, repo).stream()
            .map(PullRequest::number)
            .max(Integer::compareTo);

        if (latestIssue.isPresent() && latestIssue.get() > link.getLatestIssueNumber()) {
            return true;
        }
        if (latestPR.isPresent() && latestPR.get() > link.getLatestPRNumber()) {
            return true;
        }
        OffsetDateTime updatedAt = repository.updatedAt();
        return l.getLastUpdate().isBefore(updatedAt);
    }
}
