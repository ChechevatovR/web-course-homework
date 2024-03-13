package edu.java.scrapper.exception;

import java.nio.charset.Charset;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class UnsupportedLink extends HttpClientErrorException {
    public UnsupportedLink(
        String message,
        HttpHeaders headers,
        byte[] body,
        Charset responseCharset
    ) {
        super(
            message,
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            headers,
            body,
            responseCharset
        );
    }

    public UnsupportedLink(String message) {
        this(message, null, null, null);
    }
}
