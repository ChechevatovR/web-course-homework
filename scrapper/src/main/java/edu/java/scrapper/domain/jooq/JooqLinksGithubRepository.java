package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.LinksGithubRepository;
import edu.java.scrapper.domain.jooq.tables.Links;
import edu.java.scrapper.domain.jooq.tables.LinksGithub;
import edu.java.scrapper.domain.model.GithubLink;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;

public class JooqLinksGithubRepository implements LinksGithubRepository {
    public static final LinksGithub LINKS_GITHUB = LinksGithub.LINKS_GITHUB;
    public static final Links LINKS = Links.LINKS;
    public final DSLContext dslContext;

    public JooqLinksGithubRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public JooqLinksGithubRepository(DataSource dataSource) {
        this(new DefaultDSLContext(dataSource, SQLDialect.POSTGRES));
    }

    @Override
    public GithubLink add(GithubLink link) {
        dslContext.insertInto(LINKS_GITHUB)
            .values(link)
            .execute();
        return link;
    }

    @Override
    public GithubLink findById(int id) {
        return dslContext.select(LINKS_GITHUB.asterisk())
            .from(LINKS_GITHUB)
            .join(LINKS).on(LINKS.ID.eq(LINKS_GITHUB.ID))
            .where(LINKS_GITHUB.ID.eq(id))
            .fetchOneInto(GithubLink.class);
    }

    @Override
    public void update(GithubLink link) {
        dslContext.update(LINKS_GITHUB)
            .set(LINKS_GITHUB.LATEST_ISSUE_NUMBER, link.getLatestIssueNumber())
            .set(LINKS_GITHUB.LATEST_PR_NUMBER, link.getLatestPRNumber())
            .where(LINKS_GITHUB.ID.eq(link.getId()))
            .execute();
    }
}
