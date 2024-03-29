package edu.java.bot;

import edu.java.bot.controllers.model.ApiErrorResponse;
import java.net.URI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestExceptionHandlerTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testUnknownEndpoint() {
        ResponseEntity<ApiErrorResponse> response =
            restTemplate.getForEntity(URI.create("http://localhost:" + port + "/"), ApiErrorResponse.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), Integer.valueOf(response.getBody().getCode()));
    }

    @Test
    void testMethodNotSupported() {
        ResponseEntity<ApiErrorResponse> response =
            restTemplate.getForEntity(URI.create("http://localhost:" + port + "/updates"), ApiErrorResponse.class);

        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), Integer.valueOf(response.getBody().getCode()));
    }

    @Test
    void testNoPayload() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<ApiErrorResponse> response = restTemplate.exchange(
            URI.create("http://localhost:" + port + "/updates"),
            HttpMethod.POST,
            entity,
            ApiErrorResponse.class
        );

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(
            HttpStatus.BAD_REQUEST.value(),
            Integer.valueOf(response.getBody().getCode())
        );
        Assertions.assertTrue(response.getBody().getDescription().contains("request body is missing"));
    }

    @Test
    void testInvalidPayload() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(
            """
            {
                "id": 123,
                "tgChatIds": []
            }
            """,
            headers
        );

        ResponseEntity<ApiErrorResponse> response = restTemplate.exchange(
            URI.create("http://localhost:" + port + "/updates"),
            HttpMethod.POST,
            entity,
            ApiErrorResponse.class
        );

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), Integer.valueOf(response.getBody().getCode()));
        Assertions.assertTrue(response.getBody().getDescription().contains("Invalid request content"));
    }
}
