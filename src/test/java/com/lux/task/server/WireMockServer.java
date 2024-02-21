package com.lux.task.server;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.ContentTypes;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.lux.task.config.BooksApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Slf4j
@Component
@ConditionalOnProperty(name = "wiremock.enabled")
public class WireMockServer {
    private final BooksApiConfig config;
    private final Resource dir;

    public WireMockServer(BooksApiConfig config, @Value("${wiremock.dir}") Resource dir) {
        this.config = config;
        this.dir = dir;
    }

    @Bean(destroyMethod = "shutdownServer")
    public com.github.tomakehurst.wiremock.WireMockServer wireMock() {
        WireMockConfiguration options = options()
                .globalTemplating(true);
        com.github.tomakehurst.wiremock.WireMockServer wireMockServer = new com.github.tomakehurst.wiremock.WireMockServer(options);

        wireMockServer.start();
        wireMockServer.addStubMapping(readAllBooks());
        wireMockServer.addStubMapping(readBookById());
        wireMockServer.addStubMapping(updateBook());
        wireMockServer.addStubMapping(createBook());
        log.info("Started WireMock Server");
        return wireMockServer;
    }

    private WireMockConfiguration options() {
        return WireMockConfiguration.options()
                .port(config.getPort())
                .notifier(new ConsoleNotifier(true))
                .usingFilesUnderClasspath(dir.getFilename());
    }

    private StubMapping readAllBooks() {
        UrlPattern basePath = urlEqualTo(resolveBasePath());
        return stubMapping(WireMock::get, basePath, "books.json", 200)
                .build();
    }

    private StubMapping readBookById() {
        String basePath = resolveBasePath();
        return stubMapping(WireMock::get, urlPathTemplate(basePath + "/{bookId}"), "bookByIdTemplate.json", 200)
                .withPathParam("bookId", matching("[0-9]+"))
                .build();
    }

    private StubMapping updateBook() {
        String basePath = resolveBasePath();
        return stubMapping(WireMock::put, urlPathTemplate(basePath + "/{bookId}"), "updateBookTemplate.json", 200)
                .withPathParam("bookId", matching("[0-9]+"))
                .build();
    }

    private StubMapping createBook() {
        UrlPattern basePath = urlEqualTo(resolveBasePath());
        return stubMapping(WireMock::post, basePath, "createBookTemplate.json", 200)
                .build();
    }

    private MappingBuilder stubMapping(Function<UrlPattern, MappingBuilder> method, UrlPattern urlPattern, String filename,
                                       int status) {
        return method.apply(urlPattern)
                .withBasicAuth(config.getUsername(), config.getPassword())
                .willReturn(aResponse()
                        .withBodyFile(filename)
                        .withHeader(ContentTypes.CONTENT_TYPE, ContentTypes.APPLICATION_JSON)
                        .withStatus(status));
    }

    private String resolveBasePath() {
        String basePath = config.getBasePath();
        return basePath.startsWith("/") ? basePath : "/" + basePath;
    }

}
