package edu.java.scrapper.configuration;

public class StackOverflowClient {
    public String baseUrl = "https://api.stackexchange.com/2.3";
    public String filter = "!BByLnZ1QNx_zLScMFi.8Pz6HIwMox1_ImiqaT5V1oBML_rwYaA-OOGYnkF0NLH*5-jYC--cO4ynL";

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
