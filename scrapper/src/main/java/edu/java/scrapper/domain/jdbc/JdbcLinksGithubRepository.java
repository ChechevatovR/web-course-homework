package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.LinksGithubRepository;
import edu.java.scrapper.domain.model.GithubLink;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLinksGithubRepository implements LinksGithubRepository {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssx");
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert jdbcInsertLinksGithub;

    public JdbcLinksGithubRepository(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
        jdbcInsertLinksGithub = new SimpleJdbcInsert(jdbc)
            .withTableName("links_github");
    }

    @Override
    public GithubLink add(GithubLink link) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("latest_issue_number", link.getLatestIssueNumber());
        parameterSource.addValue("latest_pr_number", link.getLatestPRNumber());
        parameterSource.addValue("id", link.getId());
        jdbcInsertLinksGithub.execute(parameterSource);
        return link;
    }

    @Override
    public GithubLink findById(int id) {
        try {
            return jdbc.queryForObject(
                """
                SELECT *
                FROM links
                JOIN links_github lg ON links.id = lg.id
                WHERE links.id = ?
                """,
                GithubLinkRowMapper.INSTANCE,
                id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void update(GithubLink link) {
        jdbc.update(
            """
            UPDATE links_github
            SET latest_pr_number = ?,
                latest_issue_number = ?
            WHERE id = ?
            """,
            link.getLatestPRNumber(),
            link.getLatestIssueNumber(),
            link.getId()
        );
    }

    public static class GithubLinkRowMapper implements RowMapper<GithubLink> {
        public static final GithubLinkRowMapper INSTANCE = new GithubLinkRowMapper();

        private GithubLinkRowMapper() {
        }

        @Override
        @SuppressWarnings("MagicNumber")
        public GithubLink mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new GithubLink(
                rs.getInt(1),
                URI.create(rs.getString(4)),
                OffsetDateTime.parse(rs.getString(2), DATE_TIME_FORMATTER),
                OffsetDateTime.parse(rs.getString(3), DATE_TIME_FORMATTER),
                rs.getInt(6),
                rs.getInt(7)
            );
        }
    }
}
