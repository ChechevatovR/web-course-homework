package edu.java.scrapper.clients;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class GZIPDecompressingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    @NotNull
    public ClientHttpResponse intercept(
        @NotNull HttpRequest request,
        byte @NotNull [] body,
        ClientHttpRequestExecution execution
    ) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        List<String> encoding = response.getHeaders().get(HttpHeaders.CONTENT_ENCODING);
        if (encoding == null || encoding.size() != 1 || !Objects.equals(encoding.get(0), "gzip")) {
            return response;
        } else {
            return new GZIPCompressedResponse(response);
        }
    }

    public static class GZIPCompressedResponse implements ClientHttpResponse {
        public final ClientHttpResponse delegate;
        private final HttpHeaders headers;
        private final byte[] decompressedBody;

        public GZIPCompressedResponse(ClientHttpResponse delegate) throws IOException {
            this.delegate = delegate;
            headers = new HttpHeaders();
            headers.addAll(delegate.getHeaders());
            headers.remove(HttpHeaders.CONTENT_ENCODING);

            GZIPInputStream stream = new GZIPInputStream(delegate.getBody());
            decompressedBody = stream.readAllBytes();

            headers.setContentLength(decompressedBody.length);
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
        public InputStream getBody() {
            return new ByteArrayInputStream(decompressedBody);
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
