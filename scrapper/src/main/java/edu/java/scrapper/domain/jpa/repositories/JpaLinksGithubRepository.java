package edu.java.scrapper.domain.jpa.repositories;

import edu.java.scrapper.domain.LinksGithubRepository;
import edu.java.scrapper.domain.jpa.entities.Link;
import edu.java.scrapper.domain.jpa.repositories.delegates.DelegateJpaLinksGithubRepository;
import edu.java.scrapper.domain.jpa.repositories.delegates.DelegateJpaLinksRepository;
import edu.java.scrapper.domain.model.GithubLink;
import java.net.URI;

public class JpaLinksGithubRepository implements LinksGithubRepository {

    public final DelegateJpaLinksGithubRepository delegate;
    public final DelegateJpaLinksRepository delegateLinks;

    public JpaLinksGithubRepository(
        DelegateJpaLinksGithubRepository delegate,
        DelegateJpaLinksRepository delegateLinks
    ) {
        this.delegate = delegate;
        this.delegateLinks = delegateLinks;
    }

    public static GithubLink entityToDao(edu.java.scrapper.domain.jpa.entities.GithubLink githubLink) {
        return new GithubLink(
            githubLink.getId(),
            githubLink.getLink().getLastCheck(),
            githubLink.getLink().getLastUpdate(),
            URI.create(githubLink.getLink().getUrl()),
            githubLink.getLatestIssueNumber(),
            githubLink.getLatestPrNumber()
        );
    }

    public static edu.java.scrapper.domain.jpa.entities.GithubLink daoToEntity(GithubLink githubLink) {
        edu.java.scrapper.domain.jpa.entities.Link link = new edu.java.scrapper.domain.jpa.entities.Link(
            githubLink.getId(),
            githubLink.getLastCheck(),
            githubLink.getLastUpdate(),
            githubLink.getUrl().toString()
        );
        return new edu.java.scrapper.domain.jpa.entities.GithubLink(
            githubLink.getId(),
            githubLink.getLatestIssueNumber(),
            githubLink.getLatestPRNumber(),
            link
        );
    }

    @Override
    public GithubLink add(GithubLink link) {
        edu.java.scrapper.domain.jpa.entities.GithubLink entity = daoToEntity(link);
        Link entityLink = delegateLinks.save(entity.getLink());
        entity.setId(entityLink.getId());
        entity.setLink(entityLink);
        GithubLink result = entityToDao(delegate.save(entity));
        link.setId(result.getId());
        delegate.flush();
        return result;
    }

    @Override
    public GithubLink findById(final int id) {
        return delegate.findById(id).map(JpaLinksGithubRepository::entityToDao).orElse(null);
    }

    @Override
    public void update(final GithubLink link) {
        add(link);
    }

}
