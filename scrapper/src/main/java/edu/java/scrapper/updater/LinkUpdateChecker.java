package edu.java.scrapper.updater;

import edu.java.scrapper.domain.model.Link;

public interface LinkUpdateChecker {
    boolean isLinkSupported(Link link);

    boolean isUpdated(Link link);
}
