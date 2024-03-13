package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.AbstractLinksRepositoryTest;

public class JdbcLinksRepositoryTest extends AbstractLinksRepositoryTest {

    public JdbcLinksRepositoryTest() {
        super(new JdbcLinksRepository(dataSource));
    }
}
