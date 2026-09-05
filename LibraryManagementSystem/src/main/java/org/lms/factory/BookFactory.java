package org.lms.factory;

import org.lms.entity.Book;
import org.lms.exception.MissingArgumentException;

public class BookFactory {
    public static Book createBook(String isbn, String title, String author, int year) {
        if (isbn == null || isbn.isBlank() || title == null || title.isBlank()) {
            throw new MissingArgumentException("ISBN and title are required");
        }
        return new Book(isbn, title, author, year);
    }
}