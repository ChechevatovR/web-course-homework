package edu.java.scrapper.domain.jpa.repositories.delegates;

import edu.java.scrapper.domain.jpa.entities.LinkTracking;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DelegateJpaTrackingRepository
    extends JpaRepository<
    LinkTracking,
    LinkTracking.CompositeKey
    > {
    Collection<LinkTracking> findAllByChatId(int chatId);

    Collection<LinkTracking> findAllByLinkId(int linkId);
}
