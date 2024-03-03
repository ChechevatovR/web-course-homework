package edu.java.bot.configuration;

public class ScrapperClient {
    String baseUrl = "http://localhost:8080/";

    public String getBaseUrl() {
        return baseUrl;
    }

    void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
