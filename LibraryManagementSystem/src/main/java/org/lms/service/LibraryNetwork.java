package org.lms.service;


import org.lms.entity.Book;
import org.lms.entity.Patron;
import org.lms.strategy.RecommendationStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LibraryNetwork {
    private final Map<String, LibraryBranch> branches = new ConcurrentHashMap<>();
    private final Map<String, Patron> patrons = new ConcurrentHashMap<>();
    private RecommendationStrategy recommendationStrategy;

    public void addBranch(LibraryBranch branch) {
        branches.put(branch.getBranchId(), branch);
    }

    public void addPatron(Patron patron) {
        patrons.put(patron.getId(), patron);
    }

    public void updatePatron(String id, String name, String email) {
        Patron p = patrons.get(id);
        if (p != null) {
            if (name != null) p.setName(name);
            if (email != null) p.setEmail(email);
        }
    }

    public void transferBook(String isbn, String fromBranchId, String toBranchId) {
        LibraryBranch from = branches.get(fromBranchId);
        LibraryBranch to = branches.get(toBranchId);
        if (from == null || to == null) throw new IllegalArgumentException("Invalid branch");

        Book book = from.getAllBooks().stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (!book.isAvailable()) {
            throw new IllegalStateException("Cannot transfer a borrowed book");
        }

        from.removeBook(isbn);
        to.addBook(book);
    }

    public void setRecommendationStrategy(RecommendationStrategy strategy) {
        this.recommendationStrategy = strategy;
    }

    public List<Book> recommend(String patronId, int maxResults) {
        Patron patron = patrons.get(patronId);
        if (patron == null || recommendationStrategy == null) return List.of();

        List<Book> allBooks = new ArrayList<>();
        branches.values().forEach(b -> allBooks.addAll(b.getAllBooks()));
        return recommendationStrategy.recommend(patron, allBooks, maxResults);
    }

}