package edu.java.bot.controllers;

import edu.java.bot.controllers.model.LinkUpdate;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class UpdatesApiController implements UpdatesApi {
    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdate linkUpdate) {
        log.trace("updatesPost: " + linkUpdate);
        return UpdatesApi.super.updatesPost(linkUpdate);
    }
}
