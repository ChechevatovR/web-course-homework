package edu.java.scrapper.clients.stackoverflow.model.api;

import java.util.List;

public record Wrapper<T>(
    List<T> items
) {}
