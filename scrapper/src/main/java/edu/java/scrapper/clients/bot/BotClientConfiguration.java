package edu.java.scrapper.clients.bot;

import edu.java.scrapper.clients.common.openapi.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotClientConfiguration {
    @Value("#{@applicationConfig.clients.bot.baseUrl}")
    public String baseUrl;

    @Bean
    public BotApi botClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(baseUrl);
        return new BotApi(apiClient);
    }
}
