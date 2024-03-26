package edu.java.scrapper.clients;

import edu.java.scrapper.configuration.RetryConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.retry.backoff.ThreadWaitSleeper;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;

@Configuration
public class RetryingInterceptorConfiguration {
    @Value("#{@applicationConfig.clients.retry}")
    public RetryConfiguration retryConfiguration;

    @Bean
    @SuppressWarnings("MagicNumber")
    RetryingInterceptor retryingInterceptor() {
        RetryPolicy retryPolicy;
        if (retryConfiguration.getMaxRetryAttempts() == -1) {
            retryPolicy = new AlwaysRetryPolicy();
        } else {
            retryPolicy = new SimpleRetryPolicy(retryConfiguration.getMaxRetryAttempts() + 1);
        }
        BackOffPolicy backOffPolicy = switch (retryConfiguration.getBackoffPolicy()) {
            case FIXED -> {
                FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
                fixedBackOffPolicy.setBackOffPeriod(500);
                yield fixedBackOffPolicy;
            }
            case LINEAR -> new LinearBackoffPolicy(500, 500);
            case EXPONENTIAL -> {
                ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
                exponentialBackOffPolicy.setInitialInterval(300);
                exponentialBackOffPolicy.setMultiplier(1.5);
                yield exponentialBackOffPolicy;
            }
        };
        return new RetryingInterceptor(
            retryPolicy,
            backOffPolicy,
            retryConfiguration.getRetryFor()
        );
    }

    public static class LinearBackoffPolicy implements BackOffPolicy {
        private final long initialDelay;
        private final long addDelay;
        private final Sleeper sleeper;

        public LinearBackoffPolicy(long initialDelay, long addDelay) {
            this(
                initialDelay,
                addDelay,
                new ThreadWaitSleeper()
            );
        }

        public LinearBackoffPolicy(long initialDelay, long addDelay, Sleeper sleeper) {
            this.sleeper = sleeper;
            this.initialDelay = initialDelay;
            this.addDelay = addDelay;
        }

        @Override
        public BackOffContext start(final RetryContext context) {
            return new LinearBackoffContext(initialDelay);
        }

        @Override
        public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
            LinearBackoffContext linearBackoffContext = (LinearBackoffContext) backOffContext;
            try {
                sleeper.sleep(linearBackoffContext.currentDelay);
            } catch (InterruptedException e) {
                throw new BackOffInterruptedException("Backoff interrupted", e);
            }
            linearBackoffContext.currentDelay += addDelay;
        }

        public static class LinearBackoffContext implements BackOffContext {
            private long currentDelay;

            public LinearBackoffContext(long currentDelay) {
                this.currentDelay = currentDelay;
            }
        }
    }
}
