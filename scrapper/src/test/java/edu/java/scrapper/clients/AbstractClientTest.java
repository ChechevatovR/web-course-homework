package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpHeaders;

public abstract class AbstractClientTest {
    public static final Path TEST_RESOURCES = Path.of("src", "test", "resources");
    public static final int PORT = 8089;
    public final Path SERVER_ROOT = TEST_RESOURCES.resolve("apiResponses").resolve(resourceDirectoryName());

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.options()
        .port(PORT)
        .usingFilesUnderDirectory(SERVER_ROOT.toString())
        .httpDisabled(false)
    );

    @BeforeEach
    void startWM() {
        wireMockRule.start();
    }

    @AfterEach
    void stopWM() {
        wireMockRule.stop();
    }

    public abstract String resourceDirectoryName();

    protected void addWireMockStub(String url) throws IOException {
        Path responseFile = TEST_RESOURCES
            .resolve("apiResponses")
            .resolve(resourceDirectoryName())
            .resolve(url.substring(1).replace('/', '.') + ".json");


        wireMockRule.stubFor(
            WireMock.get(WireMock.urlPathEqualTo(url))
                .withPort(PORT)
                .willReturn(WireMock.ok()
                    .withBody(Files.readString(responseFile))
                    .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                )
        );
    }
}
