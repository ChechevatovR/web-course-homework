package edu.java.scrapper.domain.model;

import edu.java.scrapper.exception.UnsupportedLink;
import java.net.URI;
import java.time.OffsetDateTime;

public class StackoverflowLink extends Link {
    public static final String QUESTION_PATH_NOT_FOUND = "Question id not found";
    private final long questionId;

    @SuppressWarnings("MagicNumber")
    public StackoverflowLink(
        Integer id,
        URI url,
        OffsetDateTime lastCheck,
        OffsetDateTime lastUpdate
    ) {
        super(id, url, lastCheck, lastUpdate);
        if (getSite() != Site.STACKOVERFLOW) {
            throw new UnsupportedLink("Not a Stackoverflow link");
        }
        String path = url.getPath();
        if (path == null) {
            throw new UnsupportedLink(QUESTION_PATH_NOT_FOUND);
        }
        String[] splitted = path.split("/");
        if (splitted.length != 3 && splitted.length != 4) {
            throw new UnsupportedLink(QUESTION_PATH_NOT_FOUND);
        }
        if (!splitted[0].isEmpty() || !"questions".equals(splitted[1])) {
            throw new UnsupportedLink(QUESTION_PATH_NOT_FOUND);
        }
        questionId = Long.parseLong(splitted[2]);
    }

    public StackoverflowLink(Link link) {
        this(link.getId(), link.getUrl(), link.getLastCheck(), link.getLastUpdate());
    }

    public long getQuestionId() {
        return questionId;
    }
}
