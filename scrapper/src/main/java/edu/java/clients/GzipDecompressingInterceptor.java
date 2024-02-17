package edu.java.clients;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class GzipDecompressingInterceptor implements ClientHttpRequestInterceptor {

    public static final String CONTENT_ENCODING = "content-encoding";

    @Override
    public ClientHttpResponse intercept(
        HttpRequest request,
        byte[] body,
        ClientHttpRequestExecution execution
    ) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        List<String> encoding = response.getHeaders().get(CONTENT_ENCODING);
        if (encoding == null || encoding.size() != 1 || !Objects.equals(encoding.get(0), "gzip")) {
            return response;
        } else {
            return new GzipCompressedResponse(response);
        }
    }

    public static class GzipCompressedResponse implements ClientHttpResponse {
        public final ClientHttpResponse delegate;
        private final HttpHeaders headers;

        public GzipCompressedResponse(ClientHttpResponse delegate) {
            this.delegate = delegate;
            headers = new HttpHeaders();
            headers.addAll(delegate.getHeaders());
            headers.remove(CONTENT_ENCODING);
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
            GZIPInputStream stream = new GZIPInputStream(delegate.getBody());
            return stream;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
