package edu.java.scrapper.domain;

import edu.java.scrapper.domain.model.GithubLink;

public interface LinksGithubRepository {
    GithubLink add(GithubLink link);

    GithubLink findById(int id);

    void update(GithubLink link);
}
