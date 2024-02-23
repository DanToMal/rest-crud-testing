package com.lux.task.cucumber.steps;

import com.lux.task.client.RestClient;
import com.lux.task.cucumber.BooksContext;
import com.lux.task.model.Book;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Hooks extends AbstractBooksStepDefinitions {
    private static final int EXPECTED_STATUS_ON_DELETE = 200; //todo shouldn't be 202 or 204?

    public Hooks(RestClient<Book, Long, Response> restClient, BooksContext booksContext) {
        super(restClient, booksContext);
    }

    @Given("Client is able to access Books API")
    public void userCanAccessBooksAPI() {
        Response response = restClient.head();
        responseStatusCodeIs(response, EXPECTED_STATUS_ON_DELETE);
    }

    @After
    public void cleanUp() {
        List<Book> books = booksContext.getBooks();
        if (!books.isEmpty()) {
            log.info("Deleting {} entities created by test", books.size());
            books.forEach(book -> restClient.delete(book.getId()));
        }
    }
}
