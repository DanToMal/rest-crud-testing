package com.lux.task.cucumber.steps;

import com.lux.task.client.RestClient;
import com.lux.task.cucumber.BooksContext;
import com.lux.task.exception.TestException;
import com.lux.task.model.Book;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
public class CreateBookStepDefinitions extends AbstractBooksStepDefinitions {
    private static final int EXPECTED_STATUS_ON_CREATE = 200; //todo shouldn't be 201?

    public CreateBookStepDefinitions(RestClient<Book, Long, Response> restClient, BooksContext booksContext) {
        super(restClient, booksContext);
    }

    @Then("Following Book is created")
    public void followingBookIsCreated(Book expected) {
        Response response = restClient.create(expected);
        Book actual = parseResponse(response, new TypeRef<>() {
        });
        booksContext.add(actual);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
        responseStatusCodeIs(response, EXPECTED_STATUS_ON_CREATE);
    }

    @When("Already existing book is created")
    public void alreadyExistingBookIsCreated() {
        List<Book> books = booksContext.getBooks();
        Book book = books.stream().findAny()
                .orElseGet(this::findAnyExistingBook);

        Response response = restClient.create(book);
        responseStatusCodeIs(response, EXPECTED_STATUS_ON_CREATE);
    }

    private Book findAnyExistingBook() {
        Response allBooks = restClient.readAll();
        List<Book> books = parseResponse(allBooks, new TypeRef<>() {
        });
        if (CollectionUtils.isEmpty(books)) {
            throw new TestException("No Books available!");
        }
        return books.stream()
                .findAny()
                .get();
    }
}