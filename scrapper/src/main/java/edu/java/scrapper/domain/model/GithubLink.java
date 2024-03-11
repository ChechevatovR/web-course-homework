package edu.java.scrapper.domain.model;

import edu.java.scrapper.exception.UnsupportedLink;
import java.net.URI;
import java.time.OffsetDateTime;

public class GithubLink extends Link {
    public static final String REPOSITORY_PATH_NOT_FOUND = "Repository path not found";
    private int latestIssueNumber;
    private int latestPRNumber;

    public GithubLink(
        Integer id,
        URI url,
        OffsetDateTime lastCheck,
        OffsetDateTime lastUpdate,
        int latestIssueNumber,
        int latestPRNumber
    ) {
        super(id, url, lastCheck, lastUpdate);
        this.latestIssueNumber = latestIssueNumber;
        this.latestPRNumber = latestPRNumber;
        checkUrlSupported(url);
    }

    public GithubLink(Link link) {
        this(link.id, link.url, link.lastCheck, link.lastUpdate, -1, -1);
    }

    public String getOwner() {
        return getOwner(url);
    }

    public String getRepo() {
        return getRepo(url);
    }

    public int getLatestIssueNumber() {
        return latestIssueNumber;
    }

    public void setLatestIssueNumber(int latestIssueNumber) {
        this.latestIssueNumber = latestIssueNumber;
    }

    public int getLatestPRNumber() {
        return latestPRNumber;
    }

    public void setLatestPRNumber(int latestPRNumber) {
        this.latestPRNumber = latestPRNumber;
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

    public static boolean isUrlSupported(final URI url) {
        try {
            checkUrlSupported(url);
            return true;
        } catch (UnsupportedLink e) {
            return false;
        }
    }

    public static String getOwner(URI url) {
        return url.getPath().split("/")[1];
    }

    public static String getRepo(URI url) {
        return url.getPath().split("/")[2];
    }
}
