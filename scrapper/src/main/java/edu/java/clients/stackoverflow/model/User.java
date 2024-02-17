package edu.java.clients.stackoverflow.model;

import org.apache.kafka.common.protocol.types.Field;

public record User(
    long id,
    String url,
    String displayName,
    int reputation
) {
}
