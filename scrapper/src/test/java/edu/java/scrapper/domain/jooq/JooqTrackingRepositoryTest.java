package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.AbstractTrackingRepositoryTest;
import edu.java.scrapper.domain.jdbc.JdbcTrackingRepository;

public class JooqTrackingRepositoryTest extends AbstractTrackingRepositoryTest {

    public JooqTrackingRepositoryTest() {
        repository = new JooqTrackingRepository(dataSource);
    }
}
