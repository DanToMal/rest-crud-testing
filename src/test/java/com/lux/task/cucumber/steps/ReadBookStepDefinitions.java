package com.lux.task.cucumber.steps;

import com.lux.task.client.RestClient;
import com.lux.task.cucumber.BooksContext;
import com.lux.task.model.Book;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.List;

public class ReadBookStepDefinitions extends AbstractBooksStepDefinitions {
    private static final int NOT_FOUND = 404;
    private static final int EXPECTED_STATUS_ON_READ = 200;

    public ReadBookStepDefinitions(RestClient<Book, Long, Response> restClient, BooksContext booksContext) {
        super(restClient, booksContext);
    }

    @Given("Client is able to access Books API")
    public void userCanAccessBooksAPI() {
        Response response = restClient.head();
        responseStatusCodeIs(response, EXPECTED_STATUS_ON_READ);
    }

    @Then("Read a particular book by ID response contains created book with {string} name")
    public void readBookByIDContainsCreatedBookWithName(String bookName) {
        Book expected = booksContext.findBookByName(bookName);
        Response response = restClient.read(expected.getId());
        Book actual = response.as(Book.class);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
        responseStatusCodeIs(response, EXPECTED_STATUS_ON_READ);
    }

    @Given("Read all books response contains created books")
    public void readAllBooksResponseContainsCreatedBooks() {
        Response response = restClient.readAll();
        List<Book> actual = parseToBookList(response);
        List<Book> expected = booksContext.getBooks();

        Assertions.assertThat(actual)
                .containsAll(expected);
        responseStatusCodeIs(response, EXPECTED_STATUS_ON_READ);
    }

    @Then("Read a particular book by ID response does not contain deleted book with {string} name")
    public void readAParticularBookByIDResponseDoesNotContainDeletedBookWithName(String bookName) {
        Book removedBook = booksContext.findRemovedBookByName(bookName);

        Response read = restClient.read(removedBook.getId());
        responseStatusCodeIs(read, NOT_FOUND);
    }

    @Then("Read all books response does not contain deleted books")
    public void readAllBooksResponseDoesNotContainDeletedBooks() {
        List<Book> removed = booksContext.getRemoved();
        Assertions.assertThat(removed)
                .as("No deleted books in the context!")
                .isNotEmpty();

        Response response = restClient.readAll();
        List<Book> actual = parseToBookList(response);

        responseStatusCodeIs(response, EXPECTED_STATUS_ON_READ);
        Assertions.assertThat(actual)
                .isNotEmpty()
                .doesNotContainAnyElementsOf(removed);
    }

    private List<Book> parseToBookList(Response response) {
        return parseResponse(response, new TypeRef<>() {
        });
    }
}
