package edu.java.scrapper.updater;

import edu.java.scrapper.clients.github.GithubClient;
import edu.java.scrapper.clients.github.model.Repository;
import edu.java.scrapper.domain.model.GithubLink;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.model.Site;
import edu.java.scrapper.exception.UnsupportedLink;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class GithubLinkUpdateChecker implements LinkUpdateChecker {

    public final GithubClient githubClient;

    public GithubLinkUpdateChecker(GithubClient githubClient) {
        this.githubClient = githubClient;
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
        Repository repository = githubClient.getRepository(
            GithubLink.getOwner(l.getUrl()),
            GithubLink.getRepo(l.getUrl())
        );
        OffsetDateTime updatedAt = repository.updatedAt();
        return l.getLastUpdate().isBefore(updatedAt);
    }
}
