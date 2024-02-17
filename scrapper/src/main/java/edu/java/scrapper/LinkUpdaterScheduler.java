package edu.java.scrapper;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class LinkUpdaterScheduler {

    @Scheduled(fixedDelayString = "#{@applicationConfig.scheduler.interval.toMillis()}")
    void update() {
        log.debug("scheduled");
    }
}
