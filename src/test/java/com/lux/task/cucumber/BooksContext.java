package com.lux.task.cucumber;

import com.lux.task.exception.TestException;
import com.lux.task.model.Book;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@ScenarioScope
@Component
public class BooksContext {
    private final HashMap<Long, Book> books;
    private final HashMap<Long, Book> removed;

    public BooksContext() {
        this.books = new HashMap<>();
        this.removed = new HashMap<>();
    }

    public void add(Book book) {
        books.put(book.getId(), book);
    }

    public List<Book> getBooks() {
        return books.values().stream().toList();
    }

    public List<Book> getRemoved() {
        return removed.values().stream().toList();
    }

    /**
     * @param bookName to find by
     * @return book from context with matching name from context matching.
     * @throws TestException if no book with provided name found in context.
     */
    public Book findBookByName(String bookName) {
        return findBookByName(getBooks(), bookName);
    }

    /**
     * @param bookName to find by
     * @return book from removed context with matching name from context matching.
     * @throws TestException if no book with provided name found in removed context.
     */
    public Book findRemovedBookByName(String bookName) {
        return findBookByName(getRemoved(), bookName);
    }

    private Book findBookByName(Collection<Book> collection, String bookName) {
        return collection.stream().filter(book -> bookName.equals(book.getName()))
                .findAny()
                .orElseThrow(() -> new TestException(String.format("Cannot find book with bookName=%s  in the context!", bookName)));
    }

    public void remove(Book book) {
        books.remove(book.getId());
        removed.put(book.getId(), book);
    }
}
