package org.lms.entity;

import org.lms.exception.NotFoundException;

public class Book {

    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private boolean available;

    public Book(
            String title,
            String author,
            String isbn,
            int publicationYear
    ) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.available = true;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public boolean isAvailable() {
        return available;
    }

    public void checkout() {
        if (!available) {
            throw new NotFoundException(
                    "Book is already checked out"
            );
        }
        available = false;
    }

    public void returnBook() {
        if (available) {
            throw new IllegalStateException(
                    "Book is already available"
            );
        }
        available = true;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", available=" + available +
                '}';
    }
}