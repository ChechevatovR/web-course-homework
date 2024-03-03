package edu.java.scrapper.configuration;

public class GithubClient {
    String baseUrl = "https://api.github.com/";

    public String getBaseUrl() {
        return baseUrl;
    }

    void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
