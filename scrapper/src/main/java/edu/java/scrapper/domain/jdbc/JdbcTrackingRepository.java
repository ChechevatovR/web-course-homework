package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.TrackingRepository;
import edu.java.scrapper.domain.model.LinkTracking;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTrackingRepository implements TrackingRepository {
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTrackingRepository(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(jdbc)
            .withTableName("tracking");
    }

    @Override
    public LinkTracking add(LinkTracking tracking) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("chat_id", tracking.getChatId());
        parameterSource.addValue("link_id", tracking.getLinkId());
        jdbcInsert.execute(parameterSource);
        return tracking;
    }

    @Override
    public boolean remove(LinkTracking tracking) {
        int affectedRows = jdbc.update(
            "DELETE from tracking WHERE chat_id = ? AND link_id = ?",
            tracking.getChatId(),
            tracking.getLinkId()
        );
        return affectedRows >= 1;
    }

    @Override
    public List<LinkTracking> findAll() {
        return jdbc.query("SELECT * FROM tracking", LinkTrackingRowMapper.INSTANCE);
    }

    @Override
    public List<LinkTracking> findByChatId(int chatId) {
        return jdbc.query(
            "SELECT * FROM tracking WHERE chat_id = ?",
            new RowMapperResultSetExtractor<>(LinkTrackingRowMapper.INSTANCE),
            chatId
        );
    }

    @Override
    public List<LinkTracking> findByLinkId(int linkId) {
        return jdbc.query(
            "SELECT * FROM tracking WHERE link_id = ?",
            new RowMapperResultSetExtractor<>(LinkTrackingRowMapper.INSTANCE),
            linkId
        );
    }

    public static class LinkTrackingRowMapper implements RowMapper<LinkTracking> {
        public static final LinkTrackingRowMapper INSTANCE = new LinkTrackingRowMapper();

        private LinkTrackingRowMapper() {
        }

        @Override
        public LinkTracking mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new LinkTracking(
                rs.getInt(1),
                rs.getInt(2)
            );
        }
    }
}
