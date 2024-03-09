package edu.java.scrapper.domain;

import edu.java.scrapper.domain.model.LinkTracking;
import java.util.Collection;

public interface TrackingRepository {
    LinkTracking add(LinkTracking tracking);

    boolean remove(LinkTracking tracking);

    Collection<LinkTracking> findAll();

    Collection<LinkTracking> findByChatId(int chatId);

    Collection<LinkTracking> findByLinkId(int linkId);

}
