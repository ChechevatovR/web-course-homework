package edu.java.scrapper;

import edu.java.scrapper.clients.bot.BotApi;
import edu.java.scrapper.clients.bot.model.LinkUpdate;
import edu.java.scrapper.domain.ChatsRepository;
import edu.java.scrapper.domain.LinksRepository;
import edu.java.scrapper.domain.TrackingRepository;
import edu.java.scrapper.domain.model.Chat;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.model.LinkTracking;
import edu.java.scrapper.updater.LinkUpdateChecker;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component @Log4j2 public class LinkUpdaterScheduler {

    @Autowired List<LinkUpdateChecker> checkers;

    @Autowired LinksRepository linksRepository;

    @Autowired TrackingRepository trackingRepository;

    @Autowired ChatsRepository chatsRepository;

    @Autowired BotApi botClient;

    @Value("#{@applicationConfig.scheduler.interval}") Duration checkInterval;

    @Scheduled(fixedDelayString = "#{@applicationConfig.scheduler.interval.toMillis()}") void update() {
        log.trace("Links update start");
        Collection<Link> links = linksRepository.findCheckedBefore(checkInterval);
        for (Link link : links) {
            OffsetDateTime now = OffsetDateTime.now();
            for (LinkUpdateChecker checker : checkers) {
                try {
                    if (!checker.isLinkSupported(link)) {
                        continue;
                    }
                    if (checker.isUpdated(link)) {
                        log.info("Found update on link " + link);
                        link.setLastUpdate(now);
                        notifyOnUpdate(link);
                        break;
                    }
                } catch (RuntimeException e) {
                    log.error(e);
                }
            }
            link.setLastCheck(now);
            linksRepository.update(link);
        }
        log.trace("Links update finish");
    }

    void notifyOnUpdate(Link link) {
        Collection<LinkTracking> trackings = trackingRepository.findByLinkId(link.getId());
        LinkUpdate update = new LinkUpdate()
            .url(link.getUrl())
            .description("Update");
        for (LinkTracking tracking : trackings) {
            Chat chat = chatsRepository.findById(tracking.getChatId());
            update.addTgChatIdsItem(chat.getTelegramChatId());
        }
        if (!update.getTgChatIds().isEmpty()) {
            botClient.updatesPost(update);
        }
    }
}
