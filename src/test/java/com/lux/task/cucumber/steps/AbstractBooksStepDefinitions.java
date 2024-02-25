package com.lux.task.cucumber.steps;

import com.lux.task.client.RestClient;
import com.lux.task.cucumber.BooksContext;
import com.lux.task.exception.TestException;
import com.lux.task.model.Book;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import wiremock.org.eclipse.jetty.http.HttpStatus;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractBooksStepDefinitions {
    protected final RestClient<Book, Long, Response> restClient;
    protected final BooksContext booksContext;

    protected void responseStatusCodeIs(Response response, int expectedStatusCode) {
        Assertions.assertThat(response.statusCode())
                .isEqualTo(expectedStatusCode);
    }

    protected Book parseResponseToBook(Response response) {
        return parseResponse(response, new TypeRef<>() {
        });
    }

    @Nullable
    protected <T> T parseResponse(Response response, TypeRef<T> typeRef) {
        if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR_500) {
            log.error("{}", response.prettyPrint());
            return null;
        }
        try {
            return response.as(typeRef);
        } catch (Exception exception) {
            log.error("Error while mapping response to  object!", exception);
            throw new TestException("Cannot parse response!");
        }
    }
}
