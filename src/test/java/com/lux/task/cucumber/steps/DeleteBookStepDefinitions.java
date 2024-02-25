package com.lux.task.cucumber.steps;

import com.lux.task.client.RestClient;
import com.lux.task.cucumber.BooksContext;
import com.lux.task.model.Book;
import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;

import java.util.List;

@Slf4j
public class DeleteBookStepDefinitions extends AbstractBooksStepDefinitions {
    private static final int EXPECTED_STATUS_ON_DELETE = 200; //todo shouldn't be 202 or 204?
    private Response response;

    public DeleteBookStepDefinitions(RestClient<Book, Long, Response> restClient, BooksContext booksContext) {
        super(restClient, booksContext);
    }

    @After
    public void cleanUp() {
        List<Book> books = booksContext.getBooks();
        if (!books.isEmpty()) {
            log.info("Deleting {} entities created by test", books.size());
            books.forEach(book -> {
                Response delete = restClient.delete(book.getId());
                responseStatusCodeIs(delete, EXPECTED_STATUS_ON_DELETE);
            });
        }
    }

    @Then("Book with {string} name is deleted")
    public void bookWithNameIsDeleted(String bookName) {
        Book bookToDelete = booksContext.findBookByName(bookName);
        response = restClient.delete(bookToDelete.getId());

        responseStatusCodeIs(response, EXPECTED_STATUS_ON_DELETE);
        booksContext.remove(bookToDelete);
        Book actual = parseResponseToBook(response);
        Assertions.assertThat(actual)
                .isEqualTo(bookToDelete);
    }

    @Then("Deleted response content is {string}")
    public void responseContentIs(String expected) {
        String responseBody = response.getBody().asString();
        Assertions.assertThat(responseBody)
                .isEqualTo(expected);
    }
}
