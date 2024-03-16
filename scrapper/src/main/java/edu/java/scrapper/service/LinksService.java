package edu.java.scrapper.service;

import edu.java.scrapper.domain.ChatsRepository;
import edu.java.scrapper.domain.LinksGithubRepository;
import edu.java.scrapper.domain.LinksRepository;
import edu.java.scrapper.domain.TrackingRepository;
import edu.java.scrapper.domain.model.Chat;
import edu.java.scrapper.domain.model.GithubLink;
import edu.java.scrapper.domain.model.IsNewWrapper;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.model.LinkTracking;
import edu.java.scrapper.exception.ChatNotFound;
import edu.java.scrapper.exception.LinkNotFound;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class LinksService {

    @Autowired
    private ChatsRepository chatsRepository;

    @Autowired
    private LinksRepository linksRepository;

    @Autowired
    private LinksGithubRepository linksGithubRepository;

    @Autowired
    private TrackingRepository trackingRepository;

    public IsNewWrapper<Link> add(long telegramChatId, URI url) {
        Link link = new Link(url);
        try {
            if (GithubLink.isUrlSupported(url)) {
                GithubLink githubLink = new GithubLink(link);
                linksGithubRepository.add(githubLink);
                link.setId(githubLink.getId());
            } else {
                linksRepository.add(link);
            }
        } catch (DuplicateKeyException e) {
            link = linksRepository.findByUrl(url);
        }

        Chat chat = chatsRepository.findByTelegramId(telegramChatId);
        if (chat == null) {
            throw new ChatNotFound();
        }
        try {
            trackingRepository.add(new LinkTracking(chat.getId(), link.getId()));
            return new IsNewWrapper<>(true, link);
        } catch (DuplicateKeyException e) {
            return new IsNewWrapper<>(false, link);
        }

    }

    public Link remove(long telegramChatId, URI url) {
        Chat chat = chatsRepository.findByTelegramId(telegramChatId);
        if (chat == null) {
            throw new ChatNotFound();
        }
        Link link = linksRepository.findByUrl(url);
        if (link == null) {
            throw new LinkNotFound();
        }
        boolean removed = trackingRepository.remove(new LinkTracking(chat.getId(), link.getId()));
        if (!removed) {
            return null;
        }
        return link;
    }

    public Collection<Link> listTrackedLinksForTelegramChat(long telegramChatId) {
        Chat chat = chatsRepository.findByTelegramId(telegramChatId);
        if (chat == null) {
            throw new ChatNotFound();
        }
        Collection<LinkTracking> trackings = trackingRepository.findByChatId(chat.getId());
        List<Link> links = trackings.stream()
            .map(t -> linksRepository.findById(t.getLinkId()))
            .toList();
        return links;
    }
}
