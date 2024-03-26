package edu.java.bot.configuration;

public class Clients {
    ScrapperClient scrapper = new ScrapperClient();
    RetryConfiguration retry = new RetryConfiguration();

    public ScrapperClient getScrapper() {
        return scrapper;
    }

    void setScrapper(ScrapperClient scrapper) {
        this.scrapper = scrapper;
    }

    public RetryConfiguration getRetry() {
        return retry;
    }

    public void setRetry(RetryConfiguration retry) {
        this.retry = retry;
    }
}
