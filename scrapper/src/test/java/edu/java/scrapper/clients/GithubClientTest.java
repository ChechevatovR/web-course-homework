package edu.java.scrapper.clients;

import edu.java.scrapper.clients.github.GithubClient;
import edu.java.scrapper.clients.github.model.Issue;
import edu.java.scrapper.clients.github.model.PullRequest;
import edu.java.scrapper.clients.github.model.Repository;
import edu.java.scrapper.clients.github.model.User;
import java.io.IOException;
import java.net.URI;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@DirtiesContext
@TestPropertySource(properties = "app.clients.github.base-url=http://localhost:8089")
public class GithubClientTest extends AbstractClientTest {

    @Autowired
    public GithubClient client;

    @Override
    public String resourceDirectoryName() {
        return "github";
    }

    @BeforeEach
    void setUp() throws IOException {
        addWireMockStub("/repos/Kotlin/kotlinx.coroutines");
        addWireMockStub("/repos/Kotlin/kotlinx.coroutines/issues");
        addWireMockStub("/repos/Kotlin/kotlinx.coroutines/issues/4040");
        addWireMockStub("/repos/Kotlin/kotlinx.coroutines/pulls");
        addWireMockStub("/repos/Kotlin/kotlinx.coroutines/pulls/4041");
    }

    @Test
    void testGetRepo() {
        Repository expected = new Repository(
            61722736,
            URI.create("https://github.com/Kotlin/kotlinx.coroutines"),
            "kotlinx.coroutines",
            "Library support for Kotlin coroutines ",
            new User(
                1446536,
                URI.create("https://github.com/Kotlin"),
                "Kotlin"
            ),
            OffsetDateTime.parse("2024-02-16T14:43:33Z"),
            OffsetDateTime.parse("2016-06-22T13:49:21Z"),
            OffsetDateTime.parse("2024-02-17T14:24:32Z")
        );
        Repository actual = client.getRepository("Kotlin", "kotlinx.coroutines");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetIssue() {
        Issue expected = new Issue(
            2126739271,
            URI.create("https://github.com/Kotlin/kotlinx.coroutines/issues/4040"),
            4040,
            "Coroutines perform unnecessary thread switches",
            new User(
                159518155,
                URI.create("https://github.com/ListenableFuture"),
                "ListenableFuture"
            ),
            "open",
            false,
            OffsetDateTime.parse("2024-02-09T09:11:17Z"),
            OffsetDateTime.parse("2024-02-16T11:04:57Z"),
            null
        );
        Issue actual = client.getIssue("Kotlin", "kotlinx.coroutines", 4040);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetPull() {
        PullRequest expected = new PullRequest(
            1719902946,
            URI.create("https://github.com/Kotlin/kotlinx.coroutines/pull/4041"),
            4041,
            "Function containing atomic operations should be private ",
            new User(
                82594708,
                URI.create("https://github.com/mvicsokolova"),
                "mvicsokolova"
            ),
            "open",
            false,
            OffsetDateTime.parse("2024-02-09T19:44:59Z"),
            OffsetDateTime.parse("2024-02-16T17:43:20Z"),
            null,
            null
        );
        PullRequest actual = client.getPull("Kotlin", "kotlinx.coroutines", 4041);
        Assertions.assertEquals(expected, actual);
    }
}
