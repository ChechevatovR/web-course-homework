package edu.java.scrapper.domain.model;

import edu.java.scrapper.exception.UnsupportedLink;
import java.net.URI;
import java.time.OffsetDateTime;

public class GithubLink extends Link {
    public static final String REPOSITORY_PATH_NOT_FOUND = "Repository path not found";
    private final String owner;
    private final String repo;

    @SuppressWarnings("MagicNumber")
    public GithubLink(
        Integer id,
        URI url,
        OffsetDateTime lastCheck,
        OffsetDateTime lastUpdate
    ) {
        super(id, url, lastCheck, lastUpdate);
        if (getSite() != Site.GITHUB) {
            throw new UnsupportedLink("Not a Github link");
        }
        String path = url.getPath();
        if (path == null) {
            throw new UnsupportedLink(REPOSITORY_PATH_NOT_FOUND);
        }
        String[] splitted = path.split("/");
        if (splitted.length != 3 || !splitted[0].isEmpty()) {
            throw new UnsupportedLink(REPOSITORY_PATH_NOT_FOUND);
        }
        owner = splitted[1];
        repo = splitted[2];
    }

    public GithubLink(Link link) {
        this(link.getId(), link.getUrl(), link.getLastCheck(), link.getLastUpdate());
    }

    public String getOwner() {
        return owner;
    }

    public String getRepo() {
        return repo;
    }
}
