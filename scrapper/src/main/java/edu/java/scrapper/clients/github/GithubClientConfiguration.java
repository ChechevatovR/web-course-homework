package edu.java.scrapper.clients.github;

import edu.java.scrapper.clients.RetryingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class GithubClientConfiguration {
    @Value("#{@applicationConfig.clients.github.baseUrl}")
    public String baseUrl;

    @Autowired
    public RetryingInterceptor retryingInterceptor;

    @Bean
    public GithubApi githubApi() {
        RestClient restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
            .defaultHeader("Accept", "application/vnd.github+json")
            .requestInterceptor(retryingInterceptor)
            .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        GithubApi api = factory.createClient(GithubApi.class);
        return api;
    }

    @Bean
    public GithubClient githubClient(GithubApi api) {
        GithubClient client = new GithubClient(api);
        return client;
    }
}
