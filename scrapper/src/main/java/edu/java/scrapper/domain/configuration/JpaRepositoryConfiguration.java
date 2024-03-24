package edu.java.scrapper.domain.configuration;

import edu.java.scrapper.domain.jpa.repositories.JpaChatsRepository;
import edu.java.scrapper.domain.jpa.repositories.JpaLinksGithubRepository;
import edu.java.scrapper.domain.jpa.repositories.JpaLinksRepository;
import edu.java.scrapper.domain.jpa.repositories.JpaTrackingRepository;
import edu.java.scrapper.domain.jpa.repositories.delegates.DelegateJpaChatsRepository;
import edu.java.scrapper.domain.jpa.repositories.delegates.DelegateJpaLinksGithubRepository;
import edu.java.scrapper.domain.jpa.repositories.delegates.DelegateJpaLinksRepository;
import edu.java.scrapper.domain.jpa.repositories.delegates.DelegateJpaTrackingRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ConditionalOnProperty(value = "app.database-access-type", havingValue = "jpa")
@EnableJpaRepositories(basePackages = "edu.java.scrapper.domain.jpa.repositories.delegates")
public class JpaRepositoryConfiguration {

    @Bean
    JpaChatsRepository jpaChatsRepository(DelegateJpaChatsRepository delegate) {
        return new JpaChatsRepository(delegate);
    }

    @Bean
    JpaLinksRepository jpaLinksRepository(DelegateJpaLinksRepository delegate) {
        return new JpaLinksRepository(delegate);
    }

    @Bean
    JpaTrackingRepository jpaTrackingRepository(DelegateJpaTrackingRepository delegate) {
        return new JpaTrackingRepository(delegate);
    }

    @Bean
    JpaLinksGithubRepository jpaLinksGithubRepository(
        DelegateJpaLinksGithubRepository delegate,
        DelegateJpaLinksRepository delegateLinks
    ) {
        return new JpaLinksGithubRepository(delegate, delegateLinks);
    }
}
