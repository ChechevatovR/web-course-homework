package edu.java.scrapper.domain;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.domain.jdbc.JdbcLinksRepository;
import edu.java.scrapper.domain.model.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JdbcLinksRepositoryTest extends IntegrationTest {

    private LinksRepository repository = new JdbcLinksRepository(dataSource);

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE links, tracking");
        jdbcTemplate.execute(
            "INSERT INTO links VALUES " +
            "(1, '2024-03-10 13:00:00Z', '2024-03-10 12:00:00Z', 'GITHUB', 'https://github.com/Kotlin/kotlinx.coroutines'), " +
            "(2, '2024-03-10 12:00:00Z', '2024-03-10 12:00:00Z', 'STACKOVERFLOW', 'https://stackoverflow.com/questions/47824636')"
        );
        jdbcTemplate.execute("ALTER SEQUENCE links_id_seq RESTART WITH 3");
    }

    @Test
    void testAddGeneratedId() {
        Link link = new Link("https://github.com/getify/You-Dont-Know-JS/");
        repository.add(link);
        Assertions.assertNotNull(link.getId());
        Assertions.assertEquals(3, link.getId());
        Link linkFetched = jdbcTemplate.queryForObject(
            "SELECT * FROM links WHERE id = ?",
            JdbcLinksRepository.LinkRowMapper.INSTANCE,
            link.getId()
        );
        Assertions.assertNotNull(linkFetched);
        assertEquals(link, linkFetched);
    }

    @Test
    void testAddProvidedId() {
        Link link = new Link(
            3,
            URI.create("https://github.com/getify/You-Dont-Know-JS/"),
            Link.MIN_TIME,
            Link.MIN_TIME
        );
        repository.add(link);
        Assertions.assertNotNull(link.getId());
        Assertions.assertEquals(3, link.getId());
        Link linkFetched = jdbcTemplate.queryForObject(
            "SELECT * FROM links WHERE id = ?",
            JdbcLinksRepository.LinkRowMapper.INSTANCE,
            link.getId()
        );
        Assertions.assertNotNull(linkFetched);
        assertEquals(link, linkFetched);
    }

    @Test
    void testRemoveExisting() {
        boolean removed = repository.remove(1);
        Assertions.assertTrue(removed);
    }

    @Test
    void testRemoveNotExisting() {
        boolean removed = repository.remove(123);
        Assertions.assertFalse(removed);
    }

    @Test
    void testFindAll() {
        List<Link> links = repository.findAll().stream().toList();
        Assertions.assertEquals(2, links.size());
        for (int i = 1; i <= 2; i++) {
            Link link = links.get(i - 1);
            Assertions.assertEquals(i, link.getId());
        }
    }

    @Test
    void testFindById() {
        Link link = repository.findById(1);
        Assertions.assertNotNull(link);
        Assertions.assertEquals(1, link.getId());
        Assertions.assertEquals("https://github.com/Kotlin/kotlinx.coroutines", link.getUrl().toString());
    }

    @Test
    void testFindByIdNull() {
        Link link = repository.findById(4);
        Assertions.assertNull(link);
    }

    @Test
    void testFindByUrl() {
        URI url = URI.create("https://stackoverflow.com/questions/47824636");
        Link link = repository.findByUrl(url);
        Assertions.assertNotNull(link);
        Assertions.assertEquals(2, link.getId());
        Assertions.assertEquals(url, link.getUrl());
    }

    @Test
    void testFindByUrlNull() {
        Link link = repository.findById(44);
        Assertions.assertNull(link);
    }

    @Test
    void testFindCheckedBefore() {
        List<Link> links = repository.findCheckedBefore(OffsetDateTime.parse("2024-03-10T13:00:00Z")).stream().toList();
        Assertions.assertEquals(1, links.size());
        assertEquals(
            new Link(
                2,
                URI.create("https://stackoverflow.com/questions/47824636"),
                OffsetDateTime.parse("2024-03-10T12:00:00Z"),
                OffsetDateTime.parse("2024-03-10T12:00:00Z")
            ),
            links.get(0)
        );
    }

    @Test
    void testUpdate() {
        Link link = repository.findById(1);
        OffsetDateTime time = OffsetDateTime.of(2024, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        link.setLastCheck(time);
        repository.update(link);

        Link linkNew = repository.findById(1);
        Assertions.assertTrue(time.isEqual(linkNew.getLastCheck()));
    }

    public void assertEquals(Link expected, Link actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getUrl(), actual.getUrl());
        Assertions.assertTrue(expected.getLastCheck().isEqual(actual.getLastCheck()));
        Assertions.assertTrue(expected.getLastUpdate().isEqual(actual.getLastUpdate()));
        Assertions.assertEquals(expected.getSite(), actual.getSite());
    }
}
