package org.lms.entity;

import java.util.Objects;

public class Book {
    private final String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private boolean available;
    private String currentBranchId;

    public Book(String isbn, String title, String author, int publicationYear) {
        this.isbn = Objects.requireNonNull(isbn);
        this.title = Objects.requireNonNull(title);
        this.author = Objects.requireNonNull(author);
        this.publicationYear = publicationYear;
        this.available = true;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public void setCurrentBranchId(String currentBranchId) { this.currentBranchId = currentBranchId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        return isbn.equals(((Book) o).isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Book{isbn='%s', title='%s', author='%s', year=%d, available=%s, branch=%s}",
                isbn, title, author, publicationYear, available, currentBranchId);
    }
}