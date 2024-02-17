package edu.java.scrapper;

import edu.java.scrapper.clients.GZIPDecompressingInterceptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPDecompressingInterceptorTest {
    public GZIPDecompressingInterceptor interceptor = new GZIPDecompressingInterceptor();

    @Test
    void testGzipDecompression() throws IOException {
        String responseBodyStr = "A".repeat(1000);

        HttpRequest request = Mockito.mock(HttpRequest.class);
        Mockito.when(request.getMethod()).thenReturn(HttpMethod.GET);
        Mockito.when(request.getURI()).thenReturn(URI.create("http://example.com"));

        ClientHttpRequestExecution execution = Mockito.mock(ClientHttpRequestExecution.class);
        Mockito.when(execution.execute(Mockito.any(), Mockito.any())).then(invocation -> {
            byte[] requestBody = invocation.getArgument(1, byte[].class);
            byte[] responseBody = GZIPCompress(requestBody);
            Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_ENCODING, "gzip",
                HttpHeaders.CONTENT_LENGTH, Integer.toString(responseBody.length)
            );
            return new CHR(responseBody, headers);
        });

        ////////////////////////////////
        ClientHttpResponse response = interceptor.intercept(request, responseBodyStr.getBytes(), execution);

        String receivedResponse = new String(response.getBody().readAllBytes());
        Assertions.assertEquals(responseBodyStr, receivedResponse);
    }

    @Test
    void testUncompressedResponseBypass() throws IOException {
        String responseBodyStr = "A".repeat(1000);

        HttpRequest request = Mockito.mock(HttpRequest.class);
        Mockito.when(request.getMethod()).thenReturn(HttpMethod.GET);
        Mockito.when(request.getURI()).thenReturn(URI.create("http://example.com"));

        ClientHttpRequestExecution execution = Mockito.mock(ClientHttpRequestExecution.class);
        Mockito.when(execution.execute(Mockito.any(), Mockito.any())).then(invocation -> {
            byte[] requestBody = invocation.getArgument(1, byte[].class);
            Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_LENGTH, Integer.toString(requestBody.length)
            );
            return new CHR(requestBody, headers);
        });

        ////////////////////////////////
        CHR response = (CHR) interceptor.intercept(request, responseBodyStr.getBytes(), execution);

        Assertions.assertEquals(responseBodyStr, new String(response.body));
    }

    public static byte[] GZIPCompress(byte[] uncompressed) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
        gzipOutputStream.write(uncompressed);
        gzipOutputStream.close();
        return outputStream.toByteArray();
    }

    public static byte[] GZIPDecompress(byte[] compressed) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(compressed);
        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        return gzipInputStream.readAllBytes();
    }

    public static class CHR implements ClientHttpResponse {
        public final HttpStatusCode statusCode;
        public final byte[] body;
        public final HttpHeaders headers;

        public CHR(byte[] body, Map<String, String> headers) {
            this(body, new HttpHeaders());
            headers.forEach(this.headers::add);
        }

        public CHR(byte[] body, HttpHeaders headers) {
            this(HttpStatusCode.valueOf(200), body, headers);
        }

        public CHR(HttpStatusCode statusCode, byte[] body, HttpHeaders headers) {
            this.statusCode = statusCode;
            this.body = body;
            this.headers = headers;
        }

        @Override
        public HttpStatusCode getStatusCode() {
            return statusCode;
        }

        @Override
        public String getStatusText() {
            return statusCode.toString();
        }

        @Override
        public void close() {
        }

        @Override
        public InputStream getBody() {
            return new ByteArrayInputStream(body);
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
