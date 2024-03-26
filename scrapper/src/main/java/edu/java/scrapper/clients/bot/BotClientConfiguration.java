package edu.java.scrapper.clients.bot;

import edu.java.scrapper.clients.RetryingInterceptor;
import edu.java.scrapper.clients.common.openapi.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BotClientConfiguration {
    @Value("#{@applicationConfig.clients.bot.baseUrl}")
    public String baseUrl;

    @Autowired
    public RetryingInterceptor retryingInterceptor;

    @Bean
    public BotApi botClient() {
        RestTemplate template = new RestTemplate();
        template.getInterceptors().add(retryingInterceptor);
        ApiClient apiClient = new ApiClient(template);
        apiClient.setBasePath(baseUrl);
        return new BotApi(apiClient);
    }
}
