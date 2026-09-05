package org.lms.service;

import org.lms.entity.Book;
import org.lms.exception.AlreadyExistsException;
import org.lms.exception.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class BookService {

    private final Map<String, Book> library = new HashMap<>();

    // Add book
    public void addBook(Book book) {

        if (library.containsKey(book.getIsbn())) {
            throw new AlreadyExistsException(
                    "Book with ISBN already exists: " + book.getIsbn()
            );
        }
        library.put(book.getIsbn(), book);
    }

    // Remove book
    public void removeBook(String isbn) {
        Book removedBook = library.remove(isbn);
        if (removedBook == null) {
            throw new NotFoundException(
                    "No book found with ISBN: " + isbn
            );
        }
    }

    // Update book
    public void updateBook(String isbn, String title, String author, int publicationYear) {
        Book book = library.get(isbn);
        if (book == null) {
            throw new NotFoundException(
                    "No book found with ISBN: " + isbn
            );
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setPublicationYear(publicationYear);
    }

    // Search by ISBN
    public Optional<Book> searchByIsbn(String isbn) {
        return Optional.ofNullable(library.get(isbn));
    }

    // Search book by title, author
    public List<Book> searchByTitle(String title, String author) {

        return library.values()
                .stream()
                .filter(book ->
                        book.getTitle().toLowerCase().contains(title.toLowerCase())
                                && book.getAuthor().toLowerCase(Locale.ROOT).equals(title.trim().toLowerCase())
                )
                .collect(Collectors.toList());
    }

    // Display all books
    public List<Book> getAllBooks() {
        return new ArrayList<>(library.values());
    }
}