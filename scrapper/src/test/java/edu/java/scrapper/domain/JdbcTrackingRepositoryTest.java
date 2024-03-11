package edu.java.scrapper.domain;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.domain.jdbc.JdbcTrackingRepository;
import edu.java.scrapper.domain.model.LinkTracking;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JdbcTrackingRepositoryTest extends IntegrationTest {

    private TrackingRepository repository = new JdbcTrackingRepository(dataSource);

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE tracking, chats, links, links_github");
        jdbcTemplate.execute("INSERT INTO chats VALUES (1, 11), (2, 22), (3, 33)");
        jdbcTemplate.execute(
            "INSERT INTO links VALUES " +
            "(1, '2024-03-10 12:00:00', '2024-03-10 12:00:00', 'https://github.com/Kotlin/kotlinx" +
            ".coroutines'), " +
            "(2, '2024-03-10 12:00:00', '2024-03-10 12:00:00', 'https://stackoverflow" +
            ".com/questions/47824636')"
        );
        jdbcTemplate.execute("INSERT INTO tracking VALUES (1, 1), (2, 2), (1, 2)");
    }

    @Test
    void testAdd() {
        LinkTracking tracking = new LinkTracking(3, 2);
        repository.add(tracking);
        LinkTracking trackingFetched = jdbcTemplate.queryForObject(
            "SELECT * FROM tracking WHERE chat_id = ? AND link_id = ?",
            JdbcTrackingRepository.LinkTrackingRowMapper.INSTANCE,
            tracking.getChatId(),
            tracking.getLinkId()
        );
        Assertions.assertNotNull(trackingFetched);
        Assertions.assertEquals(tracking.getChatId(), trackingFetched.getChatId());
        Assertions.assertEquals(tracking.getLinkId(), trackingFetched.getLinkId());
    }

    @Test
    void testRemoveExisting() {
        boolean removed = repository.remove(new LinkTracking(1, 1));
        Assertions.assertTrue(removed);
    }

    @Test
    void testRemoveNotExisting() {
        boolean removed = repository.remove(new LinkTracking(3, 3));
        Assertions.assertFalse(removed);
    }

    @Test
    void testFindAll() {
        List<LinkTracking> trackings = repository.findAll().stream().toList();
        Assertions.assertEquals(3, trackings.size());
        Assertions.assertEquals(
            List.of(
                new LinkTracking(1, 1),
                new LinkTracking(2, 2),
                new LinkTracking(1, 2)
            ),
            trackings
        );
    }

    @Test
    void testFindById() {
        List<LinkTracking> trackings = repository.findByChatId(1).stream().toList();
        Assertions.assertEquals(
            List.of(
                new LinkTracking(1, 1),
                new LinkTracking(1, 2)
            ),
            trackings
        );
    }

    @Test
    void testFindByIdEmpty() {
        List<LinkTracking> trackings = repository.findByChatId(4).stream().toList();
        Assertions.assertTrue(trackings.isEmpty());
    }

    @Test
    void testFindByLinkId() {
        List<LinkTracking> trackings = repository.findByLinkId(2).stream().toList();
        Assertions.assertEquals(
            List.of(
                new LinkTracking(2, 2),
                new LinkTracking(1, 2)
            ),
            trackings
        );
    }

    @Test
    void testFindByLinkIdEmpty() {
        List<LinkTracking> trackings = repository.findByLinkId(3).stream().toList();
        Assertions.assertTrue(trackings.isEmpty());
    }
}
