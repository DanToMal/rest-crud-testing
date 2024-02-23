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
    public ReadBookStepDefinitions(RestClient<Book, Long, Response> restClient, BooksContext booksContext) {
        super(restClient, booksContext);
    }

    @Then("Read a particular book by ID response contains created book with {string} name")
    public void readBookByIDContainsCreatedBookWithName(String bookName) {
        Book expected = booksContext.findBookByName(bookName);
        Response response = restClient.read(expected.getId());
        Book actual = response.as(Book.class);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Given("Read all books response contains created books")
    public void readAllBooksResponseContainsCreatedBooks() {
        Response response = restClient.readAll();
        List<Book> actual = parseResponse(response, new TypeRef<>() {
        });
        List<Book> expected = booksContext.getBooks();
        Assertions.assertThat(actual)
                .containsAll(expected);
    }
}
