package edu.java.scrapper.configuration;

public class BotClient {
    public String baseUrl = "http://localhost:8090/";

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
