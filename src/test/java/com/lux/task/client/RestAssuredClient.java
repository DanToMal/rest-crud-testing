package com.lux.task.client;

import com.lux.task.config.BooksApiConfig;
import com.lux.task.model.Book;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.*;

@Slf4j
@Component
public class RestAssuredClient implements RestClient<Book, Long, Response> {
    private final RequestSpecification spec;

    public RestAssuredClient(BooksApiConfig config) {
        this.spec = booksApiRequestSpecification(config);
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
                .get("/{id}");
    }

    @Override
    public Response update(Book book) {
        return RestAssured.given(spec)
                .pathParam("id", book.getId())
                .body(book)
                .put("/{id}");
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
                .delete("/{id}");
    }

    private RequestSpecification booksApiRequestSpecification(BooksApiConfig config) {
        return new RequestSpecBuilder()
                .addFilter(new ResponseLoggingFilter())
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

