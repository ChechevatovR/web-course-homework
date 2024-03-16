package edu.java.scrapper.domain.jpa;

import edu.java.scrapper.domain.AbstractLinksGithubRepositoryTest;
import edu.java.scrapper.domain.jooq.JooqLinksGithubRepository;
import edu.java.scrapper.domain.jooq.JooqLinksRepository;
import edu.java.scrapper.domain.jpa.repositories.JpaLinksGithubRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@DirtiesContext
@TestPropertySource(properties = "app.database-access-type=jpa")
public class JpaLinksGithubRepositoryTest extends AbstractLinksGithubRepositoryTest {
    @Autowired
    private JpaLinksGithubRepository repository;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        super.repository = repository;
    }
}
