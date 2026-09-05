package org.lms.service;

import org.lms.entity.Book;
import org.lms.entity.BorrowingRecord;
import org.lms.entity.Patron;
import org.lms.exception.NotFoundException;

import java.util.NoSuchElementException;

public class LendingService {

    private final BookService library;
    private final PatronService patronManager;

    public LendingService(
            BookService library,
            PatronService patronManager
    ) {
        this.library = library;
        this.patronManager = patronManager;
    }

    public void checkoutBook(
            int patronId,
            String isbn
    ) {

        // Find patron
        Patron patron = patronManager
                .getPatron(patronId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Patron not found: " + patronId
                        )
                );

        // Find book
        Book book = library
                .searchByIsbn(isbn)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Book not found: " + isbn
                        )
                );
        if (!book.isAvailable()) {
            throw new NotFoundException(
                    "Book is already checked out"
            );
        }
        book.checkout();

        // Create borrowing record
        BorrowingRecord record =
                new BorrowingRecord(book);

        // Add record to patron's history
        patron.addBorrowingRecord(record);

        System.out.println(
                "Book checked out successfully"
        );
    }

    public void returnBook(
            int patronId,
            String isbn
    ) {

        Patron patron = patronManager
                .getPatron(patronId)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Patron not found: " + patronId
                        )
                );

        // Find the active borrowing record
        BorrowingRecord record = patron
                .getBorrowingHistory()
                .stream()
                .filter(r ->
                        r.getBook()
                                .getIsbn()
                                .equals(isbn)
                                && !r.isReturned()
                )
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "No active borrowing record found"
                        )
                );

        // Mark record as returned
        record.markAsReturned();

        // Make book available
        record.getBook().returnBook();

        System.out.println(
                "Book returned successfully"
        );
    }
}