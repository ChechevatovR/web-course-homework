package edu.java.scrapper.domain.jpa.repositories.delegates;

import edu.java.scrapper.domain.jpa.entities.Link;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DelegateJpaLinksRepository extends
    JpaRepository<Link, Integer> {

    Optional<Link> findByUrl(String url);

    Collection<Link> findByLastCheckBefore(OffsetDateTime lastCheck);
}
