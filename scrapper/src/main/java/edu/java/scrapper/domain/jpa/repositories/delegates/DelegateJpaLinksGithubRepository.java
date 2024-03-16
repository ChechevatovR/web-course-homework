package edu.java.scrapper.domain.jpa.repositories.delegates;

import edu.java.scrapper.domain.jpa.entities.GithubLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DelegateJpaLinksGithubRepository
    extends JpaRepository<GithubLink, Integer> {

}
