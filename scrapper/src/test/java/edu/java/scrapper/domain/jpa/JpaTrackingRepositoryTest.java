package edu.java.scrapper.domain.jpa;

import edu.java.scrapper.domain.AbstractTrackingRepositoryTest;
import edu.java.scrapper.domain.jooq.JooqTrackingRepository;
import edu.java.scrapper.domain.jpa.repositories.JpaTrackingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@DirtiesContext
@TestPropertySource(properties = "app.database-access-type=jpa")
public class JpaTrackingRepositoryTest extends AbstractTrackingRepositoryTest {
    @Autowired
    private JpaTrackingRepository repository;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        super.repository = repository;
    }
}
