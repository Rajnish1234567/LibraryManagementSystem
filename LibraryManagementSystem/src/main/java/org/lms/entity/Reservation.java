package org.lms.entity;

import java.time.LocalDateTime;

public class Reservation {
    private final String id;
    private final Book book;
    private final Patron patron;
    private final LocalDateTime reservedAt;
    private boolean fulfilled;

    public Reservation(String id, Book book, Patron patron) {
        this.id = id;
        this.book = book;
        this.patron = patron;
        this.reservedAt = LocalDateTime.now();
        this.fulfilled = false;
    }

    public String getId() { return id; }
    public Book getBook() { return book; }
    public Patron getPatron() { return patron; }
    public LocalDateTime getReservedAt() { return reservedAt; }
    public boolean isFulfilled() { return fulfilled; }
    public void setFulfilled(boolean fulfilled) { this.fulfilled = fulfilled; }
}