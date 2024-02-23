package com.lux.task.client;

import com.lux.task.config.BooksApiConfig;
import com.lux.task.model.Book;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.*;

@Slf4j
@Component
public class RestAssuredClient implements RestClient<Book, Long, Response> {
    private static final String ID_PATH_PARAM = "/{id}";
    private final RequestSpecification spec;

    public RestAssuredClient(BooksApiConfig config, RestAssuredLogFilter logFilter) {
        this.spec = booksApiRequestSpecification(config)
                .filter(logFilter);
    }

    @Override
    public Response head() {
        return RestAssured.given(spec)
                .head();
    }

    @Override
    public Response readAll() {
        return RestAssured.given(spec)
                .get();
    }

    @Override
    public Response read(Long id) {
        return RestAssured.given(spec)
                .pathParam("id", id)
                .get(ID_PATH_PARAM);
    }

    @Override
    public Response update(Book book) {
        return RestAssured.given(spec)
                .pathParam("id", book.getId())
                .body(book)
                .put(ID_PATH_PARAM);
    }

    @Override
    public Response create(Book book) {
        return RestAssured.given(spec)
                .body(book)
                .post();
    }

    @Override
    public Response delete(Long id) {
        return RestAssured.given(spec)
                .pathParam("id", id)
                .delete(ID_PATH_PARAM);
    }

    private RequestSpecification booksApiRequestSpecification(BooksApiConfig config) {
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl())
                .setBasePath(config.getBasePath())
                .setPort(config.getPort())
                .setAuth(preemptive().basic(config.getUsername(), config.getPassword()))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setRelaxedHTTPSValidation()
                .build();
    }
}

