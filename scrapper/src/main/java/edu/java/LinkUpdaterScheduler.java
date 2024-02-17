package edu.java;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class LinkUpdaterScheduler {

    @Scheduled(fixedDelayString = "#{${app.scheduler.interval}}")
    void update() {
        System.err.println("scheduled");
    }
}
