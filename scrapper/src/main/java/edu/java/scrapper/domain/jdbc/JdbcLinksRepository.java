package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.LinksRepository;
import edu.java.scrapper.domain.model.Link;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcLinksRepository implements LinksRepository {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssx");
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcLinksRepository(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(jdbc)
            .withTableName("links")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Link add(Link link) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("last_check", link.getLastCheck().format(DATE_TIME_FORMATTER));
        parameterSource.addValue("last_update", link.getLastUpdate().format(DATE_TIME_FORMATTER));
        parameterSource.addValue("site", link.getSite());
        parameterSource.addValue("url", link.getUrl());
        if (link.getId() == null) {
            Number id = jdbcInsert.executeAndReturnKey(parameterSource);
            link.setId(id.intValue());
        } else {
            parameterSource.addValue("id", link.getId());
            jdbcInsert.execute(parameterSource);
        }
        return link;
    }

    @Override
    public boolean remove(int id) {
        int affectedRows = jdbc.update("DELETE from links WHERE id = ?", id);
        return affectedRows >= 1;
    }

    @Override
    public List<Link> findAll() {
        return jdbc.query("SELECT * FROM links", LinkRowMapper.INSTANCE);
    }

    @Override
    public Link findById(int id) {
        try {
            return jdbc.queryForObject(
                "SELECT * FROM links WHERE id = ?",
                LinkRowMapper.INSTANCE,
                id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Link findByUrl(URI url) {
        try {
            return jdbc.queryForObject(
                "SELECT * FROM links WHERE url = ?",
                LinkRowMapper.INSTANCE,
                url.toString()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Link> findCheckedBefore(final OffsetDateTime dateTime) {
        return jdbc.query(
            "SELECT * FROM links WHERE last_check < ?::timestamptz",
            new RowMapperResultSetExtractor<>(LinkRowMapper.INSTANCE),
            dateTime.format(DATE_TIME_FORMATTER)
        );
    }

    @Override
    public void update(Link link) {
        jdbc.update(
            """
            UPDATE links
            SET last_check = ?::timestamptz,
                last_update = ?::timestamptz,
                url = ?
            WHERE id = ?
            """,
            link.getLastCheck().format(DATE_TIME_FORMATTER),
            link.getLastUpdate().format(DATE_TIME_FORMATTER),
            link.getUrl().toString(),
            link.getId()
        );
    }

    public static class LinkRowMapper implements RowMapper<Link> {
        public static final LinkRowMapper INSTANCE = new LinkRowMapper();

        private LinkRowMapper() {
        }

        @Override
        @SuppressWarnings("MagicNumber")
        public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Link(
                rs.getInt(1),
                URI.create(rs.getString(4)),
                OffsetDateTime.parse(rs.getString(2), DATE_TIME_FORMATTER),
                OffsetDateTime.parse(rs.getString(3), DATE_TIME_FORMATTER)
            );
        }
    }
}
