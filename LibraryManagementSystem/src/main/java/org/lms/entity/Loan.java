package org.lms.entity;

import java.time.LocalDate;

public class Loan {
    private static final int loanDays = 20;
    private final Book book;
    private final Patron patron;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(Book book, Patron patron, LocalDate checkoutDate) {
        this.book = book;
        this.patron = patron;
        this.checkoutDate = checkoutDate;
        this.dueDate = checkoutDate.plusDays(loanDays);
    }

    public Book getBook() { return book; }
    public Patron getPatron() { return patron; }
    public LocalDate getCheckoutDate() { return checkoutDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void markReturned(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isActive() {
        return returnDate == null;
    }
}