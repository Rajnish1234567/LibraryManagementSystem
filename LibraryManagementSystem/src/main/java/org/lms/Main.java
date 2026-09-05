package org.lms;

import org.lms.entity.Book;
import org.lms.entity.Patron;
import org.lms.factory.BookFactory;
import org.lms.factory.PatronFactory;
import org.lms.notification.observer.EmailSender;
import org.lms.service.LibraryBranch;
import org.lms.service.LibraryNetwork;
import org.lms.service.ReservationService;
import org.lms.strategy.HistoryBasedRecommendation;
import org.lms.strategy.TitleSearchStrategy;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        // Setup logging
        Logger root = Logger.getLogger("");
        root.setLevel(Level.INFO);
        root.addHandler(new ConsoleHandler());

        ReservationService reservationService = new ReservationService();
        LibraryNetwork network = new LibraryNetwork();

        LibraryBranch mainBranch = new LibraryBranch("B1", "Main Library", reservationService);
        LibraryBranch eastBranch = new LibraryBranch("B2", "East Branch", reservationService);
        network.addBranch(mainBranch);
        network.addBranch(eastBranch);

        mainBranch.setSearchStrategy(new TitleSearchStrategy());
        network.setRecommendationStrategy(new HistoryBasedRecommendation());

        // Create books & patrons via factories
        Book b1 = BookFactory.createBook("978-0134685991", "Effective Java", "Joshua Bloch", 2018);
        Book b2 = BookFactory.createBook("978-0596009205", "Head First Design Patterns", "Eric Freeman", 2004);
        mainBranch.addBook(b1);
        mainBranch.addBook(b2);

        Patron alice = PatronFactory.createPatron("P1", "Alice", "alice@example.com");
        network.addPatron(alice);
        reservationService.registerObserver(new EmailSender(alice.getEmail()));

        // Checkout
        mainBranch.checkout("978-0134685991", alice);

        // Reserve
        reservationService.reserve(b1, alice); // already checked out

        // Return → triggers notification
        mainBranch.returnBook("978-0134685991");

        // Search
        System.out.println(mainBranch.search("Effective"));

        // Recommendations
        System.out.println(network.recommend("P1", 5));

        // Transfer
        network.transferBook("978-0596009205", "B1", "B2");
    }
}