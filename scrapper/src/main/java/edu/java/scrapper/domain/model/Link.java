package edu.java.scrapper.domain.model;

import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.StringJoiner;

public class Link {
    public static final OffsetDateTime MIN_TIME = OffsetDateTime.ofInstant(Instant.EPOCH, ZoneId.of("GMT"));
    protected Integer id;
    protected OffsetDateTime lastCheck;
    protected OffsetDateTime lastUpdate;
    protected URI url;

    public Link(
        Integer id,
        OffsetDateTime lastCheck,
        OffsetDateTime lastUpdate,
        URI url
    ) {
        this.id = id;
        this.lastCheck = lastCheck;
        this.lastUpdate = lastUpdate;
        this.url = url;
        Site.forHost(url.getHost());
    }

    public Link(URI url) {
        this(
            null,
            MIN_TIME,
            MIN_TIME,
            url
        );
    }

    public Link(String url) {
        this(URI.create(url));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public URI getUrl() {
        return url;
    }

    public OffsetDateTime getLastCheck() {
        return lastCheck;
    }

    public OffsetDateTime getLastUpdate() {
        return lastUpdate;
    }

    public Site getSite() {
        return Site.forHost(url.getHost());
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public void setLastCheck(OffsetDateTime lastCheck) {
        this.lastCheck = lastCheck;
    }

    public void setLastUpdate(OffsetDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Link.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("url=" + url)
            .add("lastCheck=" + lastCheck)
            .add("lastUpdate=" + lastUpdate)
            .toString();
    }
}
