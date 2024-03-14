package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.AbstractTrackingRepositoryTest;

public class JdbcTrackingRepositoryTest extends AbstractTrackingRepositoryTest {

    public JdbcTrackingRepositoryTest() {
        super(new JdbcTrackingRepository(dataSource));
    }
}