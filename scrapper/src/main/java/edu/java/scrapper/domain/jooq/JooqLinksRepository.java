package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.LinksRepository;
import edu.java.scrapper.domain.jooq.tables.Links;
import edu.java.scrapper.domain.model.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;

public class JooqLinksRepository implements LinksRepository {
    public static final Links LINKS = Links.LINKS;
    public final DSLContext dslContext;

    public JooqLinksRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public JooqLinksRepository(DataSource dataSource) {
        this(new DefaultDSLContext(dataSource, SQLDialect.POSTGRES));
    }

    @Override
    public Link add(Link link) {
        if (link.getId() == null) {
            Integer id = dslContext.insertInto(LINKS, LINKS.URL, LINKS.LAST_CHECK, LINKS.LAST_UPDATE)
                .values(link.getUrl().toString(), link.getLastCheck(), link.getLastUpdate())
                .returningResult(LINKS.ID)
                .fetchSingle(LINKS.ID);
            link.setId(id);
        } else {
            dslContext.insertInto(LINKS, LINKS.ID, LINKS.URL, LINKS.LAST_CHECK, LINKS.LAST_UPDATE)
                .values(link.getId(), link.getUrl().toString(), link.getLastCheck(), link.getLastUpdate())
                .execute();
        }
        return link;
    }

    @Override
    public boolean remove(int id) {
        int affectedRows = dslContext.deleteFrom(LINKS)
            .where(LINKS.ID.eq(id))
            .execute();
        return affectedRows >= 1;
    }

    @Override
    public List<Link> findAll() {
        return dslContext.select(LINKS.asterisk())
            .from(LINKS)
            .fetchInto(Link.class);
    }

    @Override
    public Link findById(int id) {
        return dslContext.select(LINKS.asterisk())
            .from(LINKS)
            .where(LINKS.ID.eq(id))
            .fetchOneInto(Link.class);
    }

    @Override
    public Link findByUrl(URI url) {
        return dslContext.select(LINKS.asterisk())
            .from(LINKS)
            .where(LINKS.URL.eq(url.toString()))
            .fetchOneInto(Link.class);
    }

    @Override
    public List<Link> findCheckedBefore(final OffsetDateTime dateTime) {
        return dslContext.select(LINKS.asterisk())
            .from(LINKS)
            .where(LINKS.LAST_CHECK.lt(dateTime))
            .fetchInto(Link.class);
    }

    @Override
    public void update(Link link) {
        dslContext.update(LINKS)
            .set(LINKS.URL, link.getUrl().toString())
            .set(LINKS.LAST_CHECK, link.getLastCheck())
            .set(LINKS.LAST_UPDATE, link.getLastUpdate())
            .where(LINKS.ID.eq(link.getId()))
            .execute();
    }
}
