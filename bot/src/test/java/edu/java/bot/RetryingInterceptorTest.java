package edu.java.bot;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;

public class RetryingInterceptorTest {
    public static final byte[] EMPTY_BODY = new byte[0];
    public RetryPolicy retryPolicy = new SimpleRetryPolicy(3);
    public FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
    public RetryingInterceptor interceptor = new RetryingInterceptor(
        retryPolicy,
        backOffPolicy,
        List.of(HttpStatusCode::is5xxServerError)
    );

    public static HttpRequest getRequestMock() {
        return Mockito.mock(HttpRequest.class);
    }

    public static ClientHttpResponse getResponseMock(HttpStatusCode status) {
        try {
            ClientHttpResponse response = Mockito.mock(ClientHttpResponse.class);
            Mockito.when(response.getStatusCode()).thenReturn(status);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ClientHttpRequestExecution getExecutionMock(List<HttpStatusCode> responseCodes) {
        try {
            ClientHttpRequestExecution execution = Mockito.mock(ClientHttpRequestExecution.class);
            List<ClientHttpResponse> responses = responseCodes.stream()
                .map(RetryingInterceptorTest::getResponseMock)
                .toList();
            if (!responses.isEmpty()) {
                OngoingStubbing<ClientHttpResponse> stub =
                    Mockito.when(execution.execute(Mockito.any(), Mockito.any()));
                for (ClientHttpResponse response : responses) {
                    stub = stub.thenReturn(response);
                }
            }
            return execution;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        backOffPolicy.setBackOffPeriod(10);
    }

    @Test
    void testNoRetry() throws IOException {
        HttpRequest requestMock = getRequestMock();
        ClientHttpRequestExecution execution = getExecutionMock(List.of(
            HttpStatus.OK,
            HttpStatus.INTERNAL_SERVER_ERROR
            ));

        ClientHttpResponse response = interceptor.intercept(requestMock, EMPTY_BODY, execution);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRetryOnce() throws IOException {
        HttpRequest requestMock = getRequestMock();
        ClientHttpRequestExecution execution = getExecutionMock(List.of(
            HttpStatus.INTERNAL_SERVER_ERROR,
            HttpStatus.OK,
            HttpStatus.INTERNAL_SERVER_ERROR
        ));

        ClientHttpResponse response = interceptor.intercept(requestMock, EMPTY_BODY, execution);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRetryIO() throws IOException {
        HttpRequest requestMock = getRequestMock();
        ClientHttpResponse responseMock = getResponseMock(HttpStatus.OK);
        ClientHttpRequestExecution execution = Mockito.mock(ClientHttpRequestExecution.class);
        Mockito.when(execution.execute(Mockito.any(), Mockito.any()))
            .thenThrow(new IOException())
            .thenThrow(new IOException())
            .thenReturn(responseMock);

        ClientHttpResponse response = interceptor.intercept(requestMock, EMPTY_BODY, execution);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRetryExhausted() {
        HttpRequest requestMock = getRequestMock();
        ClientHttpRequestExecution execution = getExecutionMock(List.of(
            HttpStatus.INTERNAL_SERVER_ERROR
        ));

        RuntimeException exception = Assertions.assertThrows(
            RuntimeException.class,
            () -> interceptor.intercept(requestMock, EMPTY_BODY, execution)
        );
        Assertions.assertTrue(exception.getMessage().contains("Retry"));
        Assertions.assertTrue(exception.getMessage().contains("500"));
    }

    @Test
    void testBackoff() throws IOException {
        backOffPolicy.setBackOffPeriod(500);

        HttpRequest requestMock = getRequestMock();
        ClientHttpRequestExecution execution = getExecutionMock(List.of(
            HttpStatus.INTERNAL_SERVER_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR,
            HttpStatus.OK
        ));

        long startTime = System.currentTimeMillis();
        interceptor.intercept(requestMock, EMPTY_BODY, execution);
        long finishTime = System.currentTimeMillis();

        Assertions.assertTrue(finishTime - startTime > 500 * 2);
    }
}
