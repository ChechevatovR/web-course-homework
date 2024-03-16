package edu.java.scrapper.domain;

import edu.java.scrapper.domain.model.Link;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;
import org.springframework.stereotype.Repository;

@Repository
public interface LinksRepository {
    Link add(Link link);

    boolean remove(int id);

    Collection<Link> findAll();

    Link findById(int id);

    Link findByUrl(URI url);

    Collection<Link> findCheckedBefore(OffsetDateTime dateTime);

    default Collection<Link> findCheckedBefore(Duration ago) {
        return findCheckedBefore(OffsetDateTime.now().minus(ago));
    }

    void update(Link link);
}
