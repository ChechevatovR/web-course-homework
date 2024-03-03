package edu.java.scrapper.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class TgChatApiController implements TgChatApi {
    @Override
    public ResponseEntity<Void> tgChatIdDelete(final Long id) {
        log.trace("tgChatIdDelete: " + id);
        return TgChatApi.super.tgChatIdDelete(id);
    }

    @Override
    public ResponseEntity<Void> tgChatIdPost(final Long id) {
        log.trace("tgChatIdPost: " + id);
        return TgChatApi.super.tgChatIdPost(id);
    }
}
