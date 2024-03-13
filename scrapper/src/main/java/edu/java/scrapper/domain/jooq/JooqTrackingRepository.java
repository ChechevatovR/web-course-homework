package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.TrackingRepository;
import edu.java.scrapper.domain.jooq.tables.Tracking;
import edu.java.scrapper.domain.model.LinkTracking;
import java.util.List;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;

public class JooqTrackingRepository implements TrackingRepository {
    public static final Tracking TRACKING = Tracking.TRACKING;
    public final DSLContext dslContext;

    public JooqTrackingRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public JooqTrackingRepository(DataSource dataSource) {
        this(new DefaultDSLContext(dataSource, SQLDialect.POSTGRES));
    }

    @Override
    public LinkTracking add(LinkTracking tracking) {
        dslContext.insertInto(TRACKING, TRACKING.CHAT_ID, TRACKING.LINK_ID)
            .values(tracking.getChatId(), tracking.getLinkId())
            .execute();
        return tracking;
    }

    @Override
    public boolean remove(LinkTracking tracking) {
        int affectedRows = dslContext.deleteFrom(TRACKING)
            .where(TRACKING.CHAT_ID.eq(tracking.getChatId()))
            .and(TRACKING.LINK_ID.eq(tracking.getLinkId()))
            .execute();
        return affectedRows >= 1;
    }

    @Override
    public List<LinkTracking> findAll() {
        return dslContext.select(TRACKING.asterisk())
            .from(TRACKING)
            .fetchInto(LinkTracking.class);
    }

    @Override
    public List<LinkTracking> findByChatId(int chatId) {
        return dslContext.select(TRACKING.asterisk())
            .from(TRACKING)
            .where(TRACKING.CHAT_ID.eq(chatId))
            .fetchInto(LinkTracking.class);
    }

    @Override
    public List<LinkTracking> findByLinkId(int linkId) {
        return dslContext.select(TRACKING.asterisk())
            .from(TRACKING)
            .where(TRACKING.LINK_ID.eq(linkId))
            .fetchInto(LinkTracking.class);
    }
}
