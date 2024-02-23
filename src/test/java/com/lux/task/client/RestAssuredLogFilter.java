package com.lux.task.client;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wiremock.org.eclipse.jetty.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Objects;

/**
 * Logs ech HTTP call summary and details in the case of client/server error.
 * Saves HTTP responses and requests into the file under {@link RestAssuredLogFilter#outputDir} directory.
 */
@Slf4j
@Component
public class RestAssuredLogFilter implements Filter {
    private final String outputDir;

    public RestAssuredLogFilter(@Value("${test-evidences.dir}") String outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        int responseStatusCode = response.getStatusCode();

        String description = String.format("%s %s => %s %s", requestSpec.getMethod(), requestSpec.getURI(), responseStatusCode, response.getStatusLine());
        log.info("{} ", description);

        String details = new Formatter()
                .format("%s %n", description)
                .format("Request Body => %s %n", Objects.toString(requestSpec.getBody(), ""))
                .format("Response Status => %s Response Body => %s", responseStatusCode, response.getBody().prettyPrint())
                .toString();

        boolean isError = HttpStatus.isClientError(responseStatusCode) || HttpStatus.isServerError(responseStatusCode);
        if (isError) {
            log.error("{}", details);
        }

        logToFile(requestSpec, details, isError);
        return response;
    }

    private void logToFile(FilterableRequestSpecification requestSpec, String content, boolean isError) {
        String filename = resolveFilename(requestSpec, isError);
        try {
            String current = new File(".").getPath();
            Path logDir = Paths.get(current, outputDir);
            if (!logDir.toFile().exists()) {
                Files.createDirectories(logDir);
            }
            Files.write(logDir.resolve(filename), content.getBytes(), StandardOpenOption.CREATE_NEW);
            log.info("saved {}", filename);
        } catch (IOException e) {
            log.error("Error while saving {} to file!", filename, e);
        }
    }

    private String resolveFilename(FilterableRequestSpecification requestSpec, boolean isError) {
        String method = requestSpec.getMethod();
        String timeStamp = DateTimeFormatter.ISO_INSTANT
                .format(Instant.now());
        String basePath = requestSpec.getBasePath().replaceAll("/", "-");
        String errorPrefix = isError ? "ERROR_" : "";
        return String.format("%s%s_%s_%s.log", errorPrefix, basePath, method, timeStamp);
    }
}