package edu.java.scrapper.domain.model;

import edu.java.scrapper.exception.UnsupportedLink;
import java.net.URI;
import java.time.OffsetDateTime;

public class GithubLink extends Link {
    public static final String REPOSITORY_PATH_NOT_FOUND = "Repository path not found";

    public GithubLink(
        Integer id,
        URI url,
        OffsetDateTime lastCheck,
        OffsetDateTime lastUpdate
    ) {
        super(id, url, lastCheck, lastUpdate);
        checkUrlSupported(url);
    }

    public String getOwner() {
        return getOwner(url);
    }

    public String getRepo() {
        return getRepo(url);
    }

    @SuppressWarnings("MagicNumber")
    public static void checkUrlSupported(final URI url) {
        if (!Site.GITHUB.host.equals(url.getHost())) {
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
    }

    public static String getOwner(URI url) {
        return url.getPath().split("/")[1];
    }

    public static String getRepo(URI url) {
        return url.getPath().split("/")[2];
    }
}
