package com.lux.task.cucumber.steps;

import com.lux.task.client.RestClient;
import com.lux.task.cucumber.BooksContext;
import com.lux.task.exception.TestException;
import com.lux.task.model.Book;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.OptionalLong;

public class UpdateBookStepDefinitions extends AbstractBooksStepDefinitions {
    private static final int EXPECTED_STATUS_ON_UPDATE = 200;

    public UpdateBookStepDefinitions(RestClient<Book, Long, Response> restClient, BooksContext booksContext) {
        super(restClient, booksContext);
    }

    @Given("Created book with {string} name is updated as follows")
    public void createdBookWithNameIsUpdatedAsFollows(String bookName, Book toUpdate) {
        Book book = booksContext.findBookByName(bookName);
        book.setName(toUpdate.getName());
        book.setAuthor(toUpdate.getAuthor());
        book.setPublication(toUpdate.getPublication());
        book.setCategory(toUpdate.getCategory());
        book.setPages(toUpdate.getPages());
        book.setPrice(toUpdate.getPrice());

        Response response = restClient.update(book);
        responseStatusCodeIs(response, EXPECTED_STATUS_ON_UPDATE);
    }

    @When("Following Book with non existing ID is updated")
    public void followingBookWithNonExistingIDIsUpdated(Book toUpdate) {
        long ID = findMaxBookID().orElse(0L) + 1;
        toUpdate.setId(ID);

        Response response = restClient.update(toUpdate);
        booksContext.add(toUpdate);
        responseStatusCodeIs(response, EXPECTED_STATUS_ON_UPDATE);
    }

    private OptionalLong findMaxBookID() {
        Response allBooks = restClient.readAll();
        List<Book> books = parseResponse(allBooks, new TypeRef<>() {
        });
        if (CollectionUtils.isEmpty(books)) {
            throw new TestException("No Books available!");
        }
        return books.stream()
                .mapToLong(Book::getId)
                .max();
    }
}