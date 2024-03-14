package edu.java.scrapper.domain.jdbc;

import edu.java.scrapper.domain.AbstractLinksGithubRepositoryTest;

public class JdbcLinksGithubRepositoryTest extends AbstractLinksGithubRepositoryTest {

    public JdbcLinksGithubRepositoryTest() {
        super(new JdbcLinksGithubRepository(dataSource, new JdbcLinksRepository(dataSource)));
    }
}