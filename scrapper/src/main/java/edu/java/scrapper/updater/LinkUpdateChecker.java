package edu.java.scrapper.updater;

import edu.java.scrapper.domain.model.Link;

public interface LinkUpdateChecker {
    boolean isLinkSupported(Link link);

    String isUpdated(Link link);
}
