package edu.java.scrapper.clients.stackoverflow;

import edu.java.scrapper.clients.GZIPDecompressingInterceptor;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class StackOverflowClientConfiguration {
    @Value("#{@applicationConfig.clients.stackoverflow.baseUrl}")
    public String baseUrl;

    @Value("#{@applicationConfig.clients.stackoverflow.filter}")
    public String filter;

    @Bean
    public StackOverflowApi stackOverflowApi() {
        RestClient restClient = RestClient.builder()
            .baseUrl(baseUrl + "?site={site}&filter={filter}")
            .defaultUriVariables(Map.of(
                "site", "stackoverflow",
                "filter", filter
            ))
            .requestInterceptor(new GZIPDecompressingInterceptor())
            .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        StackOverflowApi api = factory.createClient(StackOverflowApi.class);
        return api;
    }

    @Bean
    public StackOverflowClient stackOverflowClient(StackOverflowApi api) {
        StackOverflowClient client = new StackOverflowClient(api);

        return client;
    }

}
