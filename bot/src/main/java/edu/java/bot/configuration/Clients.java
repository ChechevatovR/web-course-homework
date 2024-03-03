package edu.java.bot.configuration;

public class Clients {
    ScrapperClient scrapper = new ScrapperClient();

    public ScrapperClient getScrapper() {
        return scrapper;
    }

    void setScrapper(ScrapperClient scrapper) {
        this.scrapper = scrapper;
    }
}
