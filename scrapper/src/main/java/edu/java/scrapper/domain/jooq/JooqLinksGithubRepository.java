package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.LinksGithubRepository;
import edu.java.scrapper.domain.LinksRepository;
import edu.java.scrapper.domain.jooq.tables.Links;
import edu.java.scrapper.domain.jooq.tables.LinksGithub;
import edu.java.scrapper.domain.model.GithubLink;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.transaction.annotation.Transactional;

public class JooqLinksGithubRepository implements LinksGithubRepository {
    public static final LinksGithub LINKS_GITHUB = LinksGithub.LINKS_GITHUB;
    public static final Links LINKS = Links.LINKS;
    public final DSLContext dslContext;
    public final LinksRepository linksRepository;

    public JooqLinksGithubRepository(DSLContext dslContext, LinksRepository linksRepository) {
        this.dslContext = dslContext;
        this.linksRepository = linksRepository;
    }

    public JooqLinksGithubRepository(DataSource dataSource, LinksRepository linksRepository) {
        this(new DefaultDSLContext(dataSource, SQLDialect.POSTGRES), linksRepository);
    }

    @Override
    @Transactional
    public GithubLink add(GithubLink link) {
        linksRepository.add(link);
        dslContext.insertInto(
                LINKS_GITHUB,
                LINKS_GITHUB.ID,
                LINKS_GITHUB.LATEST_ISSUE_NUMBER,
                LINKS_GITHUB.LATEST_PR_NUMBER
            )
            .values(link.getId(), link.getLatestIssueNumber(), link.getLatestPRNumber())
            .execute();
        return link;
    }

    @Override
    public GithubLink findById(int id) {
        return dslContext.select(
                LINKS.ID,
                LINKS.LAST_CHECK,
                LINKS.LAST_UPDATE,
                LINKS.URL,
                LINKS_GITHUB.LATEST_ISSUE_NUMBER,
                LINKS_GITHUB.LATEST_PR_NUMBER
            )
            .from(LINKS_GITHUB)
            .join(LINKS).on(LINKS.ID.eq(LINKS_GITHUB.ID))
            .where(LINKS_GITHUB.ID.eq(id))
            .fetchOneInto(GithubLink.class);
    }

    @Override
    @Transactional
    public void update(GithubLink link) {
        linksRepository.update(link);
        dslContext.update(LINKS_GITHUB)
            .set(LINKS_GITHUB.LATEST_ISSUE_NUMBER, link.getLatestIssueNumber())
            .set(LINKS_GITHUB.LATEST_PR_NUMBER, link.getLatestPRNumber())
            .where(LINKS_GITHUB.ID.eq(link.getId()))
            .execute();
    }
}
