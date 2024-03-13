package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.AbstractChatsRepositoryTest;

public class JdbcChatsRepositoryTest extends AbstractChatsRepositoryTest {

    public JdbcChatsRepositoryTest() {
        super(new JdbcChatsRepository(dataSource));
    }
}
