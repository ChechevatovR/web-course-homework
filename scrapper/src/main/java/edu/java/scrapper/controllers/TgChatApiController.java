package edu.java.scrapper.controllers;

import edu.java.scrapper.domain.model.Chat;
import edu.java.scrapper.domain.model.IsNewWrapper;
import edu.java.scrapper.service.ChatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TgChatApiController implements TgChatApi {

    @Autowired
    private ChatsService chatsService;

    @Override
    public ResponseEntity<Void> tgChatIdDelete(final Long id) {
        boolean deregistered = chatsService.deregister(id);
        if (deregistered) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<Void> tgChatIdPost(final Long id) {
        IsNewWrapper<Chat> registered = chatsService.register(id);
        if (registered.isNew) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
    }
}
