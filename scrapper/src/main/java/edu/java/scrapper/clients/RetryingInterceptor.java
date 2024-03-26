package edu.java.scrapper.clients;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

public class RetryingInterceptor implements ClientHttpRequestInterceptor {
    private final RetryPolicy retryPolicy;
    private final BackOffPolicy backoffPolicy;
    private final Collection<Predicate<HttpStatusCode>> retryFor;

    public RetryingInterceptor(
        RetryPolicy retryPolicy,
        BackOffPolicy backoffPolicy,
        Collection<Predicate<HttpStatusCode>> retryFor
    ) {
        this.retryPolicy = retryPolicy;
        this.backoffPolicy = backoffPolicy;
        this.retryFor = retryFor;
    }

    @Override
    @NotNull
    public ClientHttpResponse intercept(
        @NotNull HttpRequest request,
        byte @NotNull [] body,
        @NotNull ClientHttpRequestExecution execution
    ) throws IOException {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backoffPolicy);
        return retryTemplate.execute(callback(request, body, execution));
    }

    RetryCallback<ClientHttpResponse, IOException> callback(
        @NotNull HttpRequest request,
        byte @NotNull [] body,
        ClientHttpRequestExecution execution
    ) {
        return ctx -> {
            ClientHttpResponse response = execution.execute(request, body);
            HttpStatusCode responseStatus = response.getStatusCode();
            if (retryFor.stream().anyMatch(p -> p.test(responseStatus))) {
                throw new RuntimeException("Retry for status " + responseStatus);
            }
            return response;
        };
    }
}
