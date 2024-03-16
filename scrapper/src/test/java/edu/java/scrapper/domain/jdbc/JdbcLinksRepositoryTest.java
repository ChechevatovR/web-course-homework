package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.AbstractLinksRepositoryTest;

public class JdbcLinksRepositoryTest extends AbstractLinksRepositoryTest {

    public JdbcLinksRepositoryTest() {
        repository = new JdbcLinksRepository(dataSource);
    }
}
