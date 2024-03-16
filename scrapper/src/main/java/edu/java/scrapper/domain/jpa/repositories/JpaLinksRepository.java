package edu.java.scrapper.domain.jpa.repositories;

import edu.java.scrapper.domain.LinksRepository;
import edu.java.scrapper.domain.jpa.repositories.delegates.DelegateJpaLinksRepository;
import edu.java.scrapper.domain.model.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class JpaLinksRepository implements LinksRepository {
    public final DelegateJpaLinksRepository delegate;

    public JpaLinksRepository(DelegateJpaLinksRepository delegate) {
        this.delegate = delegate;
    }

    public static Link entityToDao(edu.java.scrapper.domain.jpa.entities.Link link) {
        return new Link(
            link.getId(),
            link.getLastCheck(),
            link.getLastUpdate(),
            URI.create(link.getUrl())
        );
    }

    public static edu.java.scrapper.domain.jpa.entities.Link daoToEntity(Link link) {
        return new edu.java.scrapper.domain.jpa.entities.Link(
            link.getId(),
            link.getLastCheck(),
            link.getLastUpdate(),
            link.getUrl().toString()
        );
    }

    @Override
    public Link add(final Link link) {
        Link result = entityToDao(delegate.save(daoToEntity(link)));
        link.setId(result.getId());
        delegate.flush();
        return result;
    }

    @Override
    @Transactional
    public boolean remove(final int id) {
        boolean wasRemoved = delegate.existsById(id);
        delegate.deleteById(id);
        delegate.flush();
        return wasRemoved;
    }

    @Override
    public List<Link> findAll() {
        return delegate.findAll().stream().map(JpaLinksRepository::entityToDao).toList();
    }

    @Override
    public Link findById(final int id) {
        return delegate.findById(id).map(JpaLinksRepository::entityToDao).orElse(null);
    }

    @Override
    public Link findByUrl(final URI url) {
        return delegate.findByUrl(url.toString()).map(JpaLinksRepository::entityToDao).orElse(null);
    }

    @Override
    public List<Link> findCheckedBefore(final OffsetDateTime dateTime) {
        return delegate.findByLastCheckBefore(dateTime).stream().map(JpaLinksRepository::entityToDao).toList();
    }

    @Override
    public void update(final Link link) {
        add(link);
    }

}
