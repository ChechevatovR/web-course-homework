package edu.java.scrapper.controllers;

import edu.java.scrapper.controllers.model.AddLinkRequest;
import edu.java.scrapper.controllers.model.LinkResponse;
import edu.java.scrapper.controllers.model.ListLinksResponse;
import edu.java.scrapper.controllers.model.RemoveLinkRequest;
import edu.java.scrapper.domain.model.IsNewWrapper;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.service.LinksService;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinksApiController implements LinksApi {

    @Autowired
    private LinksService linksService;

    @Override
    public ResponseEntity<LinkResponse> linksDelete(final Long tgChatId, final RemoveLinkRequest removeLinkRequest) {
        Link link = linksService.remove(tgChatId, removeLinkRequest.getLink());
        if (link == null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        LinkResponse response = new LinkResponse(link.getId().longValue(), link.getUrl());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ListLinksResponse> linksGet(final Long tgChatId) {
        Collection<Link> links = linksService.listTrackedLinksForTelegramChat(tgChatId);
        List<LinkResponse> linkResponses = links.stream()
            .map(l -> new LinkResponse(l.getId().longValue(), l.getUrl()))
            .toList();
        ListLinksResponse response = new ListLinksResponse(linkResponses);
        response.setSize(links.size());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LinkResponse> linksPost(final Long tgChatId, final AddLinkRequest addLinkRequest) {
        IsNewWrapper<Link> wrapper = linksService.add(tgChatId, addLinkRequest.getLink());
        LinkResponse response = new LinkResponse(wrapper.value.getId().longValue(), wrapper.value.getUrl());
        if (wrapper.isNew) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }
    }
}
