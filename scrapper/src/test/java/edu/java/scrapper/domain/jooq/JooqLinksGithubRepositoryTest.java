package edu.java.scrapper.domain.jooq;

import edu.java.scrapper.domain.AbstractLinksGithubRepositoryTest;

public class JooqLinksGithubRepositoryTest extends AbstractLinksGithubRepositoryTest {

    public JooqLinksGithubRepositoryTest() {
        super(new JooqLinksGithubRepository(dataSource, new JooqLinksRepository(dataSource)));
    }
}
