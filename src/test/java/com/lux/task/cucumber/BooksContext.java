package com.lux.task.cucumber;

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
}
