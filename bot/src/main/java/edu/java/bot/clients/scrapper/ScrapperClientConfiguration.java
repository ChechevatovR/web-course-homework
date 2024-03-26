package edu.java.bot.clients.scrapper;

import edu.java.bot.RetryingInterceptor;
import edu.java.bot.clients.common.openapi.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ScrapperClientConfiguration {
    @Value("#{@applicationConfig.clients.scrapper.baseUrl}")
    public String baseUrl;

    @Autowired
    public RetryingInterceptor retryingInterceptor;

    @Bean
    ScrapperApi scrapperClient() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(retryingInterceptor);
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(baseUrl);
        return new ScrapperApi(apiClient);
    }
}
