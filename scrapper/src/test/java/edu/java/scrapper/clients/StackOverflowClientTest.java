package edu.java.scrapper.clients;

import edu.java.scrapper.clients.github.GithubClientConfiguration;
import edu.java.scrapper.clients.stackoverflow.StackOverflowClient;
import edu.java.scrapper.clients.stackoverflow.StackOverflowClientConfiguration;
import edu.java.scrapper.clients.stackoverflow.model.Answer;
import edu.java.scrapper.clients.stackoverflow.model.Comment;
import edu.java.scrapper.clients.stackoverflow.model.Question;
import edu.java.scrapper.clients.stackoverflow.model.User;
import java.io.IOException;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


public class StackOverflowClientTest extends AbstractClientTest {
    public StackOverflowClient client;

    {
        StackOverflowClientConfiguration configuration = new StackOverflowClientConfiguration();
        configuration.filter = "whatever";
        client = configuration.stackOverflowClient(configuration.stackOverflowApi("http://localhost:8089"));
    }

    @Override
    public String resourceDirectoryName() {
        return "stackoverflow";
    }

    @BeforeEach
    void setUp() throws IOException {
        addWireMockStub("/questions/47824636");
    }

    @Test
    void testGetQuestion() {
        User a1010C1100 = new User(
            4965770,
            6403914,
            URI.create("https://stackoverflow.com/users/4965770/a1010c1100"),
            "A1010C1100",
            13
        );
        User scottBoston = new User(
            6361531,
            7316008,
            URI.create("https://stackoverflow.com/users/6361531/scott-boston"),
            "Scott Boston",
            149773
        );
        Question expected = new Question(
            47824636,
            URI.create("https://stackoverflow.com/questions/47824636/pandas-stacked-bar-chart-with-independent" +
                       "-unrelated-partitions-of-bars"),
            1,
            "Pandas- stacked bar chart with independent/unrelated partitions of bars",
            a1010C1100,
            true,
            List.of(
                new Answer(
                    47825151,
                    URI.create("https://stackoverflow.com/questions/47824636/pandas-stacked-bar-chart-with-independent-unrelated" +
                    "-partitions-of-bars/47825151#47825151"),
                    1,
                    "Pandas- stacked bar chart with independent/unrelated partitions of bars",
                    List.of(
                        new Comment(
                            82613297,
                            URI.create("https://stackoverflow.com/questions/47824636/pandas-stacked-bar-chart-with-independent" +
                            "-unrelated-partitions-of-bars/47825151#comment82613297_47825151"),
                            0,
                            a1010C1100,
                            OffsetDateTime.parse("2017-12-15T03:35:01Z")
                        ),
                        new Comment(
                            82613303,
                            URI.create("https://stackoverflow.com/questions/47824636/pandas-stacked-bar-chart-with" +
                                       "-independent-unrelated-partitions-of-bars/47825151#comment82613303_47825151"),
                            0,
                            scottBoston,
                            OffsetDateTime.parse("2017-12-15T03:35:33Z")
                        )
                    ),
                    OffsetDateTime.parse("2017-12-15T02:54:45Z"),
                    scottBoston,
                    OffsetDateTime.parse("2017-12-15T02:54:45Z")
                )
            ),
            OffsetDateTime.parse("2017-12-15T01:38:55Z"),
            OffsetDateTime.parse("2017-12-15T02:54:45Z"),
            List.of()
        );
        Question actual = client.getQuestion(47824636);
        Assertions.assertEquals(expected, actual);
    }
}
