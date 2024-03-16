package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.AbstractChatsRepositoryTest;

public class JooqChatsRepositoryTest extends AbstractChatsRepositoryTest {

    public JooqChatsRepositoryTest() {
        repository = new JooqChatsRepository(dataSource);
    }
}
