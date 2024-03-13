package edu.java.scrapper.domain.configuration;

import edu.java.scrapper.domain.jooq.JooqChatsRepository;
import edu.java.scrapper.domain.jooq.JooqLinksGithubRepository;
import edu.java.scrapper.domain.jooq.JooqLinksRepository;
import edu.java.scrapper.domain.jooq.JooqTrackingRepository;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "app.database-access-type", havingValue = "jooq")
public class JooqRepositoryConfiguration {

    @Bean
    JooqChatsRepository jooqChatsRepository(DataSource dataSource) {
        return new JooqChatsRepository(dataSource);
    }

    @Bean
    JooqLinksRepository jooqLinksRepository(DataSource dataSource) {
        return new JooqLinksRepository(dataSource);
    }

    @Bean
    JooqTrackingRepository jooqTrackingRepository(DataSource dataSource) {
        return new JooqTrackingRepository(dataSource);
    }

    @Bean
    JooqLinksGithubRepository jooqLinksGithubRepository(DataSource dataSource) {
        return new JooqLinksGithubRepository(dataSource, jooqLinksRepository(dataSource));
    }
}
