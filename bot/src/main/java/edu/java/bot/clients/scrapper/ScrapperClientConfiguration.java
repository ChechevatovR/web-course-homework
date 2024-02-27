package edu.java.bot.clients.scrapper;

import edu.java.bot.clients.common.openapi.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapperClientConfiguration {
    @Value("#{@applicationConfig.clients.scrapper.baseUrl}")
    public String baseUrl;

    @Bean
    DefaultApi scrapperClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(baseUrl);
        return new DefaultApi(apiClient);
    }
}
