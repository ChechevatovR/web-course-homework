package edu.java.scrapper.domain.jpa;

import edu.java.scrapper.domain.AbstractChatsRepositoryTest;
import edu.java.scrapper.domain.jpa.repositories.JpaChatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@DirtiesContext
@TestPropertySource(properties = "app.database-access-type=jpa")
public class JpaChatsRepositoryTest extends AbstractChatsRepositoryTest {
    @Autowired
    private JpaChatsRepository repository;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        super.repository = repository;
    }
}
