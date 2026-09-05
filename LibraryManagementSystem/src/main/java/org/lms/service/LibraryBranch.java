package org.lms.service;

import org.lms.entity.Book;
import org.lms.entity.Loan;
import org.lms.entity.Patron;
import org.lms.exception.NotFoundException;
import org.lms.strategy.SearchStrategy;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LibraryBranch {
    private static final Logger logger = Logger.getLogger(LibraryBranch.class.getName());

    private final String branchId;
    private final String name;
    private final Map<String, Book> inventory = new ConcurrentHashMap<>(); // ISBN -> Book
    private final Map<String, Loan> activeLoans = new ConcurrentHashMap<>(); // ISBN -> Loan

    private final ReservationService reservationService;
    private SearchStrategy searchStrategy;

    public LibraryBranch(String branchId, String name, ReservationService reservationService) {
        this.branchId = branchId;
        this.name = name;
        this.reservationService = reservationService;
    }
    public void setSearchStrategy(SearchStrategy strategy) {
        this.searchStrategy = strategy;
    }

    // Book management
    public void addBook(Book book) {
        book.setCurrentBranchId(branchId);
        inventory.put(book.getIsbn(), book);
        logger.info("Added book " + book.getIsbn() + " to branch " + branchId);
    }

    public void removeBook(String isbn) {
        Book removed = inventory.remove(isbn);
        if (removed != null) {
            logger.info("Removed book " + isbn + " from branch " + branchId);
        }
    }

    public void updateBook(String isbn, String newTitle, String newAuthor, Integer newYear) {
        Book book = inventory.get(isbn);
        if (book != null) {
            if (newTitle != null) book.setTitle(newTitle);
            if (newAuthor != null) book.setAuthor(newAuthor);
            if (newYear != null) book.setPublicationYear(newYear);
            logger.info("Updated book " + isbn);
        }
    }

    public List<Book> search(String query) {
        if (searchStrategy == null) {
            throw new NotFoundException("Search strategy not set");
        }
        return searchStrategy.search(new ArrayList<>(inventory.values()), query);
    }

    // Lending
    public boolean checkout(String isbn, Patron patron) {
        Book book = inventory.get(isbn);
        if (book == null || !book.isAvailable()) {
            logger.warning("Checkout failed for " + isbn);
            return false;
        }
        book.setAvailable(false);
        Loan loan = new Loan(book, patron, LocalDate.now());
        activeLoans.put(isbn, loan);
        patron.addLoan(loan);
        logger.info("Checked out " + isbn + " to " + patron.getId());
        return true;
    }

    public boolean returnBook(String isbn) {
        Loan loan = activeLoans.remove(isbn);
        if (loan == null) return false;
        Book book = loan.getBook();
        book.setAvailable(true);
        loan.markReturned(LocalDate.now());
        logger.info("Returned " + isbn);

        reservationService.notifyBookAvailable(book);
        return true;
    }

    public Collection<Book> getAvailableBooks() {
        return inventory.values().stream().filter(Book::isAvailable).collect(Collectors.toList());
    }

    public Collection<Book> getAllBooks() {
        return Collections.unmodifiableCollection(inventory.values());
    }

    public String getBranchId() { return branchId; }
    public String getName() { return name; }
}