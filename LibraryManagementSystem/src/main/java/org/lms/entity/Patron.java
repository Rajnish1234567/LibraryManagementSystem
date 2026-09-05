package org.lms.entity;

import java.util.*;

public class Patron {
    private final String id;
    private String name;
    private String email;
    private final List<Loan> borrowingHistory = new ArrayList<>();
    private final Set<String> preferredGenres = new HashSet<>();

    public Patron(String id, String name, String email) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Loan> getBorrowingHistory() { return Collections.unmodifiableList(borrowingHistory); }
    public void addLoan(Loan loan) { borrowingHistory.add(loan); }
    public Set<String> getPreferredGenres() { return preferredGenres; }

    @Override
    public String toString() {
        return "Patron{id='" + id + "', name='" + name + "', email='" + email + "'}";
    }
}