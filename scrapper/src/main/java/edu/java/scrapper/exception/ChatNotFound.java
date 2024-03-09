package edu.java.scrapper.exception;

import java.nio.charset.Charset;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ChatNotFound extends HttpClientErrorException {
    public ChatNotFound(
        HttpHeaders headers,
        byte[] body,
        Charset responseCharset
    ) {
        super("Chat not found", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), headers, body,
            responseCharset);
    }

    public ChatNotFound() {
        this(null, null, null);
    }
}
