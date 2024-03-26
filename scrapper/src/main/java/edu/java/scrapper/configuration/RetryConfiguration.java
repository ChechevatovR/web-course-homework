package edu.java.scrapper.configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.springframework.http.HttpStatusCode;

public class RetryConfiguration {
    public static final Map<String, Predicate<HttpStatusCode>> PREDEFINED_PREDICATES = Map.of(
        "1xx", HttpStatusCode::is1xxInformational,
        "2xx", HttpStatusCode::is2xxSuccessful,
        "3xx", HttpStatusCode::is3xxRedirection,
        "4xx", HttpStatusCode::is4xxClientError,
        "5xx", HttpStatusCode::is5xxServerError
    );

    @SuppressWarnings("MagicNumber")
    private int maxRetryAttempts = 3;
    private List<Predicate<HttpStatusCode>> retryFor = List.of(HttpStatusCode::is5xxServerError);
    private RetryBackoffPolicy backoffPolicy = RetryBackoffPolicy.FIXED;

    public List<Predicate<HttpStatusCode>> getRetryFor() {
        return retryFor;
    }

    public void setRetryFor(List<String> retryFor) {
        this.retryFor = retryFor.stream()
            .map(String::toLowerCase)
            .map(codeString -> {
                return PREDEFINED_PREDICATES.getOrDefault(
                    codeString,
                    c -> Integer.toString(c.value()).equals(codeString)
                );
            }).toList();
    }

    public RetryBackoffPolicy getBackoffPolicy() {
        return backoffPolicy;
    }

    public void setBackoffPolicy(RetryBackoffPolicy backoffPolicy) {
        this.backoffPolicy = backoffPolicy;
    }

    public int getMaxRetryAttempts() {
        return maxRetryAttempts;
    }

    public void setMaxRetryAttempts(int maxRetryAttempts) {
        this.maxRetryAttempts = maxRetryAttempts;
    }

    public enum RetryBackoffPolicy {
        FIXED, LINEAR, EXPONENTIAL;
    }

}
