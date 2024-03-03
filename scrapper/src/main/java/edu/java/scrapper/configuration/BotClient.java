package edu.java.scrapper.configuration;

public class BotClient {
    String baseUrl = "http://localhost:8090/";

    public String getBaseUrl() {
        return baseUrl;
    }

    void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
