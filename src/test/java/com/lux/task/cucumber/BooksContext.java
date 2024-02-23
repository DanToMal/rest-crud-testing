package com.lux.task.cucumber;

import com.lux.task.exception.TestException;
import com.lux.task.model.Book;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@ScenarioScope
@Component
public class BooksContext {
    private final HashMap<Long, Book> books;

    public BooksContext() {
        this.books = new HashMap<>();
    }

    public void add(Book book) {
        books.put(book.getId(), book);
    }

    public List<Book> getBooks() {
        return books.values().stream().toList();
    }

    /**
     * @param bookName to find by
     * @return book with matching name from context matching.
     * @throws TestException if no book with provided name found in context.
     */
    public Book findBookByName(String bookName) {
        return getBooks().stream().filter(book -> bookName.equals(book.getName()))
                .findAny()
                .orElseThrow(() -> new TestException(String.format("Cannot find book with bookName=%s  in the context!", bookName)));
    }
}
