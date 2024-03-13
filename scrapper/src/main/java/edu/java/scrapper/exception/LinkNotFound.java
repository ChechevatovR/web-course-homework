package edu.java.scrapper.exception;

import java.nio.charset.Charset;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class LinkNotFound extends HttpClientErrorException {
    public LinkNotFound(
        HttpHeaders headers,
        byte[] body,
        Charset responseCharset
    ) {
        super("Link not found", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), headers, body,
            responseCharset);
    }

    public LinkNotFound() {
        this(null, null, null);
    }
}
