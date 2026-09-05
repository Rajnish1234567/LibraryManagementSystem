package org.lms.entity;

import java.time.LocalDate;

public class BorrowingRecord {

    private final Book book;
    private final LocalDate borrowedDate;

    private LocalDate returnedDate;
    private boolean returned;

    public BorrowingRecord(Book book) {
        this.book = book;
        this.borrowedDate = LocalDate.now();
        this.returned = false;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void markAsReturned() {
        if (returned) {
            throw new IllegalStateException("Book is already returned");
        }

        this.returned = true;
        this.returnedDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return "BorrowingRecord{" +
                "book=" + book +
                ", borrowedDate=" + borrowedDate +
                ", returnedDate=" + returnedDate +
                ", returned=" + returned +
                '}';
    }
}