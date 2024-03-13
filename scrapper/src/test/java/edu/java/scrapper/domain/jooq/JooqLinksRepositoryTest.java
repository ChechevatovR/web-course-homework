package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.AbstractLinksRepositoryTest;
import edu.java.scrapper.domain.jdbc.JdbcLinksRepository;

public class JooqLinksRepositoryTest extends AbstractLinksRepositoryTest {

    public JooqLinksRepositoryTest() {
        super(new JooqLinksRepository(dataSource));
    }
}
