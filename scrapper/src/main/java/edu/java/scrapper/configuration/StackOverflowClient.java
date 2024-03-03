package edu.java.scrapper.configuration;

public class StackOverflowClient {
    String baseUrl = "https://api.stackexchange.com/2.3";
    String filter = "!BByLnZ1QNx_zLScMFi.8Pz6HIwMox1_ImiqaT5V1oBML_rwYaA-OOGYnkF0NLH*5-jYC--cO4ynL";

    public String getBaseUrl() {
        return baseUrl;
    }

    void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getFilter() {
        return filter;
    }

    void setFilter(String filter) {
        this.filter = filter;
    }
}
