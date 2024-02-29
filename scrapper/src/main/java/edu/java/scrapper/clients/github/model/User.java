package edu.java.scrapper.clients.github.model;

import java.net.URI;

public record User(
    long id,
    URI uri,
    String login
) {
}
