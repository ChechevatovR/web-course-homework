package edu.java.scrapper.domain;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.domain.jdbc.JdbcLinksGithubRepository;
import edu.java.scrapper.domain.model.GithubLink;
import edu.java.scrapper.domain.model.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractLinksGithubRepositoryTest extends IntegrationTest {

    protected LinksGithubRepository repository;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        jdbcTemplate.execute("TRUNCATE TABLE links, tracking, links_github");
        jdbcTemplate.execute(
            "INSERT INTO links VALUES " +
            "(1, '2024-03-10 13:00:00Z', '2024-03-10 12:00:00Z', 'https://github.com/Kotlin/kotlinx.coroutines'), " +
            "(2, '2024-03-11 13:00:00Z', '2024-03-11 12:00:00Z', 'https://stackoverflow.com/questions/47824636'), " +
            "(3, '2024-03-12 13:00:00Z', '2024-03-12 12:00:00Z', 'https://github.com/shd/logic2023') "
        );
        jdbcTemplate.execute(
            "INSERT INTO links_github VALUES " +
            "(1, 101, 102), " +
            "(3, 301, 302) "
        );
        jdbcTemplate.execute("ALTER SEQUENCE links_id_seq RESTART WITH 4");
    }

    @Test
    void testAddGeneratedId() {
        GithubLink link = new GithubLink(
            null,
            Link.MIN_TIME,
            Link.MIN_TIME,
            URI.create("https://github.com/getify/You-Dont-Know-JS/"),
            401,
            402
        );
        repository.add(link);
        Assertions.assertNotNull(link.getId());
        Assertions.assertEquals(4, link.getId());
        GithubLink linkFetched = jdbcTemplate.queryForObject(
            "SELECT * FROM links JOIN links_github lg on links.id = lg.id WHERE links.id = ?",
            JdbcLinksGithubRepository.GithubLinkRowMapper.INSTANCE,
            link.getId()
        );
        Assertions.assertNotNull(linkFetched);
        assertEquals(link, linkFetched);
    }

    @Test
    void testAddProvidedId() {
        GithubLink link = new GithubLink(
            4,
            Link.MIN_TIME,
            Link.MIN_TIME,
            URI.create("https://github.com/getify/You-Dont-Know-JS/"),
            401,
            402
        );
        repository.add(link);
        Assertions.assertNotNull(link.getId());
        Assertions.assertEquals(4, link.getId());
        GithubLink linkFetched = jdbcTemplate.queryForObject(
            "SELECT * FROM links JOIN links_github lg on links.id = lg.id WHERE links.id = ?",
            JdbcLinksGithubRepository.GithubLinkRowMapper.INSTANCE,
            link.getId()
        );
        Assertions.assertNotNull(linkFetched);
        assertEquals(link, linkFetched);
    }

    @Test
    void testFindById() {
        GithubLink link = repository.findById(1);
        Assertions.assertNotNull(link);
        assertEquals(
            new GithubLink(
                1,
                OffsetDateTime.parse("2024-03-10T13:00:00Z"),
                OffsetDateTime.parse("2024-03-10T12:00:00Z"),
                URI.create("https://github.com/Kotlin/kotlinx.coroutines"),
                101,
                102
            ),
            link
        );
    }

    @Test
    void testFindByIdNull() {
        Link link = repository.findById(4);
        Assertions.assertNull(link);
    }

    @Test
    void testUpdate() {
        GithubLink link = repository.findById(1);
        link.setLatestPRNumber(1001);
        link.setLatestIssueNumber(1002);
        repository.update(link);

        GithubLink linkNew = repository.findById(1);
        assertEquals(link, linkNew);
    }

    public void assertEquals(GithubLink expected, GithubLink actual) {
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getUrl(), actual.getUrl());
        Assertions.assertTrue(expected.getLastCheck().isEqual(actual.getLastCheck()));
        Assertions.assertTrue(expected.getLastUpdate().isEqual(actual.getLastUpdate()));
        Assertions.assertEquals(expected.getSite(), actual.getSite());
        Assertions.assertEquals(expected.getLatestIssueNumber(), actual.getLatestIssueNumber());
        Assertions.assertEquals(expected.getLatestPRNumber(), actual.getLatestPRNumber());
    }
}
