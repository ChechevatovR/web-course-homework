package edu.java.clients;

import org.apache.coyote.http11.filters.GzipOutputFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class GzipDecompressingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(
        HttpRequest request,
        byte[] body,
        ClientHttpRequestExecution execution
    ) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        List<String> encoding = response.getHeaders().get("content-encoding");
        if (encoding == null || encoding.size() != 1 || !Objects.equals(encoding.get(0), "gzip")) {
            return response;
        } else {
            return new Q(response);
        }
    }

    public static class Q implements ClientHttpResponse {
        public final ClientHttpResponse delegate;
        private final HttpHeaders headers;

        public Q(ClientHttpResponse delegate) {
            this.delegate = delegate;
            headers = new HttpHeaders();
            headers.addAll(delegate.getHeaders());
            headers.remove("content-encoding");
        }

        @Override
        public HttpStatusCode getStatusCode() throws IOException {
            return delegate.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return delegate.getStatusText();
        }

        @Override
        public void close() {
            delegate.close();
        }

        @Override
        public InputStream getBody() throws IOException {
//            String s = new String(new GZIPInputStream(delegate.getBody()).readAllBytes());

            GZIPInputStream stream = new GZIPInputStream(delegate.getBody());
            return stream;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
