package edu.java.clients.github;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class GithubClientConfiguration {
    @Value("${app.clients.github.base-url :#{null}}")
    public String baseUrl;
    public static final String DEFAULT_BASE_URL = "https://api.github.com/";

    @Bean
    GithubClient githubClient() {
        String actualBaseUrl = Objects.requireNonNullElse(baseUrl, DEFAULT_BASE_URL);
        RestClient restClient = RestClient.builder()
            .baseUrl(actualBaseUrl)
            .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
            .defaultHeader("Accept", "application/vnd.github+json")
            .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        GithubApi api = factory.createClient(GithubApi.class);
        GithubClient client = new GithubClient(api);

//        Object repository = client.getRepository("Kotlin", "kotlinx.coroutines");
//        List<Issue> issues = client.getIssuesForRepository("Kotlin", "kotlinx.coroutines");
//        List<PullRequest> pulls = client.getPullsForRepository("Kotlin", "kotlinx.coroutines");
//        Issue issue = client.getIssue("Kotlin", "kotlinx.coroutines", 4040);
//        PullRequest pull = client.getPull("Kotlin", "kotlinx.coroutines", 4041);

        return client;
    }
}
