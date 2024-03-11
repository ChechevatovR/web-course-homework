package edu.java.scrapper.updater;

import edu.java.scrapper.clients.stackoverflow.StackOverflowClient;
import edu.java.scrapper.clients.stackoverflow.model.Question;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.model.Site;
import edu.java.scrapper.domain.model.StackoverflowLink;
import edu.java.scrapper.exception.UnsupportedLink;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class StackoverflowLinkUpdateChecker implements LinkUpdateChecker {

    public final StackOverflowClient stackOverflowClient;

    public StackoverflowLinkUpdateChecker(StackOverflowClient stackOverflowClient) {
        this.stackOverflowClient = stackOverflowClient;
    }

    @Override
    public boolean isLinkSupported(Link link) {
        if (link.getSite() != Site.STACKOVERFLOW) {
            return false;
        }
        try {
            new StackoverflowLink(link);
            return true;
        } catch (UnsupportedLink e) {
            return false;
        }
    }

    @Override
    public boolean isUpdated(Link l) {
        StackoverflowLink link = new StackoverflowLink(l);
        Question question = stackOverflowClient.getQuestion(link.getQuestionId());
        OffsetDateTime updatedAt = question.lastActivityDate();
        return link.getLastUpdate().isBefore(updatedAt);
    }
}
