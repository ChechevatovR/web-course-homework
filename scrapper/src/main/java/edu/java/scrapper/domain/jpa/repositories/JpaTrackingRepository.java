package edu.java.scrapper.domain.jpa.repositories;

import edu.java.scrapper.domain.TrackingRepository;
import edu.java.scrapper.domain.jpa.repositories.delegates.DelegateJpaTrackingRepository;
import edu.java.scrapper.domain.model.LinkTracking;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

public class JpaTrackingRepository implements TrackingRepository {
    public final DelegateJpaTrackingRepository delegate;

    public JpaTrackingRepository(DelegateJpaTrackingRepository delegate) {
        this.delegate = delegate;
    }

    public static LinkTracking entityToDao(edu.java.scrapper.domain.jpa.entities.LinkTracking tracking) {
        return new LinkTracking(
            tracking.getChatId(),
            tracking.getLinkId()
        );
    }

    public static edu.java.scrapper.domain.jpa.entities.LinkTracking daoToEntity(LinkTracking tracking) {
        return new edu.java.scrapper.domain.jpa.entities.LinkTracking(
            tracking.getChatId(),
            tracking.getLinkId()
        );
    }

    @Override
    public LinkTracking add(final LinkTracking tracking) {
        LinkTracking result = entityToDao(delegate.save(daoToEntity(tracking)));
        delegate.flush();
        return result;
    }

    @Override
    @Transactional
    public boolean remove(final LinkTracking tracking) {
        edu.java.scrapper.domain.jpa.entities.LinkTracking.CompositeKey id =
            new edu.java.scrapper.domain.jpa.entities.LinkTracking.CompositeKey(
                tracking.getChatId(),
                tracking.getLinkId()
            );
        boolean wasRemoved = delegate.existsById(id);
        delegate.deleteById(id);
        delegate.flush();
        return wasRemoved;
    }

    @Override
    public Collection<LinkTracking> findAll() {
        return delegate.findAll().stream().map(JpaTrackingRepository::entityToDao).toList();
    }

    @Override
    public Collection<LinkTracking> findByChatId(final int chatId) {
        return delegate.findAllByChatId(chatId).stream().map(JpaTrackingRepository::entityToDao).toList();
    }

    @Override
    public Collection<LinkTracking> findByLinkId(final int linkId) {
        return delegate.findAllByLinkId(linkId).stream().map(JpaTrackingRepository::entityToDao).toList();
    }

}
