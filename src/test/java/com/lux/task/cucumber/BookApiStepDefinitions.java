package com.lux.task.cucumber;

import com.lux.task.client.RestClient;
import com.lux.task.model.Book;
import io.cucumber.java.en.Then;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.cucumber.java.en.Given;
import org.assertj.core.api.Assertions;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class BookApiStepDefinitions {
    private final RestClient restClient;
    private final BooksContext booksContext;
    private Response response;

    @Given("Read all books response contains created books")
    public void createdBooksAreIncludedInReadAll() {
        response = restClient.getAllBooks();
        List<Book> actual = response.as(new TypeRef<>() {
        });
        List<Book> expected = booksContext.getBooks();
        Assertions.assertThat(actual)
                .containsAll(expected);
    }

    @Then("Response status code is {int}")
    public void responseStatusCodeIs(int statusCode) {
        Assertions.assertThat(response.statusCode())
                .isEqualTo(statusCode);
    }

    @Then("Book with {int} id is equal to following")
    public void bookWithIdIsEqual(int bookID, Book expected) {
        response = restClient.getBookByID(bookID);
        Book actual = response.as(Book.class);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Then("Following Book is created")
    public void followingBookIsCreated(Book expected) {
        response = restClient.createBook(expected);
        Book actual = parseResponseAndUpdateContext(response);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    private Book parseResponseAndUpdateContext(Response response) {
        try {
            Book book = response.as(Book.class);
            booksContext.add(book);
            return book;
        } catch (Exception exception) {
            log.error("Error while mapping response to Book object!", exception);
            throw new AssertionError(exception);
        }
    }
}