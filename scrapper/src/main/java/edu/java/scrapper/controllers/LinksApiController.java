package edu.java.scrapper.controllers;

import edu.java.scrapper.controllers.model.AddLinkRequest;
import edu.java.scrapper.controllers.model.LinkResponse;
import edu.java.scrapper.controllers.model.ListLinksResponse;
import edu.java.scrapper.controllers.model.RemoveLinkRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class LinksApiController implements LinksApi {
    @Override
    public ResponseEntity<LinkResponse> linksDelete(final Long tgChatId, final RemoveLinkRequest removeLinkRequest) {
        log.trace("linksDelete: " + tgChatId + " " + removeLinkRequest);
        return LinksApi.super.linksDelete(tgChatId, removeLinkRequest);
    }

    @Override
    public ResponseEntity<ListLinksResponse> linksGet(final Long tgChatId) {
        log.trace("linksGet: " + tgChatId);
        return LinksApi.super.linksGet(tgChatId);
    }

    @Override
    public ResponseEntity<LinkResponse> linksPost(final Long tgChatId, final AddLinkRequest addLinkRequest) {
        log.trace("linksPost: " + tgChatId + " " + addLinkRequest);
        return LinksApi.super.linksPost(tgChatId, addLinkRequest);
    }
}
