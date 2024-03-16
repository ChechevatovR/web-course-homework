package edu.java.scrapper.domain.configuration;

import edu.java.scrapper.domain.jdbc.JdbcChatsRepository;
import edu.java.scrapper.domain.jdbc.JdbcLinksGithubRepository;
import edu.java.scrapper.domain.jdbc.JdbcLinksRepository;
import edu.java.scrapper.domain.jdbc.JdbcTrackingRepository;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "app.database-access-type", havingValue = "jdbc")
public class JdbcRepositoryConfiguration {

    @Bean
    JdbcChatsRepository jdbcChatsRepository(DataSource dataSource) {
        return new JdbcChatsRepository(dataSource);
    }

    @Bean
    JdbcLinksRepository jdbcLinksRepository(DataSource dataSource) {
        return new JdbcLinksRepository(dataSource);
    }

    @Bean
    JdbcTrackingRepository jdbcTrackingRepository(DataSource dataSource) {
        return new JdbcTrackingRepository(dataSource);
    }

    @Bean
    JdbcLinksGithubRepository jdbcLinksGithubRepository(DataSource dataSource) {
        return new JdbcLinksGithubRepository(dataSource, jdbcLinksRepository(dataSource));
    }
}
