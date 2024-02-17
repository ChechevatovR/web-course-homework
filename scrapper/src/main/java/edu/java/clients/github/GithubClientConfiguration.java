package edu.java.clients.github;

import edu.java.clients.github.model.Issue;
import edu.java.clients.github.model.PullRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import java.util.List;

@Configuration
public class GithubClientConfiguration {

    @Bean
    GithubClient githubClient() {
        RestClient restClient = RestClient.builder()
            .baseUrl("https://api.github.com/")
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
