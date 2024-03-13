package edu.java.scrapper.domain.model;

import edu.java.scrapper.exception.UnsupportedLink;
import java.util.Arrays;
import java.util.Optional;

public enum Site {
    GITHUB("github.com"),
    STACKOVERFLOW("stackoverflow.com");

    public final String host;

    Site(String host) {
        this.host = host;
    }

    public static Site forHost(String host) {
        Optional<Site> site = Arrays.stream(values())
            .filter(s -> s.host.equals(host))
            .findAny();
        if (site.isEmpty()) {
            throw new UnsupportedLink("Unknown host");
        } else {
            return site.get();
        }
    }
}
