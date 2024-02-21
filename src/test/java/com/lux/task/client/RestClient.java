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
public class RestClient {
    private final RequestSpecification spec;

    public RestClient(BooksApiConfig config) {
        this.spec = booksApiRequestSpecification(config);
    }

    /**
     * Read all books
     */
    public Response getAllBooks() {
        return RestAssured.given(spec)
                .get();
    }

    public Response createBook(Book book) {
        return RestAssured.given(spec)
                .body(book)
                .post();
    }

    /**
     * Read a particular book by ID
     */
    public Response getBookByID(int id) {
        return RestAssured.given(spec)
                .pathParam("id", id)
                .get("/{id}");
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

