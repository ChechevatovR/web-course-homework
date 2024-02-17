package edu.java.clients.stackoverflow;

import edu.java.clients.GzipDecompressingInterceptor;
import edu.java.clients.github.GithubClient;
import edu.java.clients.stackoverflow.model.Answer;
import edu.java.clients.stackoverflow.model.Comment;
import edu.java.clients.stackoverflow.model.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Configuration
public class StackOverflowClientConfiguration {
//    @Value("#{${app.clients.stackoverflow.baseUri}}")
    public String baseUri = "https://api.stackexchange.com/2.3";
    public String filter = "!BByLnZ1QNx_zLScMFi.8Pz6HIwMox1_ImiqaT5V1oBML_rwYaA-OOGYnkF0NLH*5-jYC--cO4ynL";

    @Bean
    StackOverflowClient stackOverflowClient() {
        RestClient restClient = RestClient.builder()
            .baseUrl(baseUri + "?site={site}&filter={filter}")
            .defaultUriVariables(Map.of(
                "site", "stackoverflow",
                "filter", filter
            ))
            .requestInterceptor(new GzipDecompressingInterceptor())
            .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        StackOverflowApi api = factory.createClient(StackOverflowApi.class);
        StackOverflowClient client = new StackOverflowClient(api);

//        Object commentsForPost = client.getCommentsForPost(13);
//        Object answersForQuestion = client.getAnswersForQuestion(13);
//        Object question = client.getQuestion(13);

        return client;
    }

}
