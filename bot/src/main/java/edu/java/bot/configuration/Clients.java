package edu.java.bot.configuration;

public class Clients {
    public ScrapperClient scrapper = new ScrapperClient();

    public ScrapperClient getScrapper() {
        return scrapper;
    }

    public void setScrapper(ScrapperClient scrapper) {
        this.scrapper = scrapper;
    }
}
