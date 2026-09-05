package org.lms.service;

import org.lms.entity.Book;
import org.lms.entity.Patron;
import org.lms.exception.AlreadyExistsException;
import org.lms.exception.NotFoundException;

import java.util.*;

public class PatronService {

    private final Map<Integer, Patron> patrons = new HashMap<>();

    // Add new patron
    public void addPatron(Patron patron) {
        if (patrons.containsKey(patron.getPatronId())) {
            throw new AlreadyExistsException(
                    "Patron already exists with ID: "
                            + patron.getPatronId()
            );
        }
        patrons.put(patron.getPatronId(), patron);
    }

    // Update patron information
    public void updatePatron(int patronId, String name, String email, String phone) {

        Patron patron = patrons.get(patronId);

        if (patron == null) {
            throw new NotFoundException(
                    "No patron found with ID: " + patronId
            );
        }

        patron.setName(name);
        patron.setEmail(email);
        patron.setPhone(phone);
    }

    // Get patron
    public Optional<Patron> getPatron(int patronId) {
        return Optional.ofNullable(patrons.get(patronId));
    }

    // Add borrowed book to patron history
    public void addBorrowingHistory(int patronId, Book book) {

        Patron patron = patrons.get(patronId);
        if (patron == null) {
            throw new NotFoundException(
                    "No patron found with ID: " + patronId
            );
        }

        patron.addToBorrowingHistory(book);
    }

    // Get borrowing history
    public List<Book> getBorrowingHistory(int patronId) {

        Patron patron = patrons.get(patronId);
        if (patron == null) {
            throw new NotFoundException(
                    "No patron found with ID: " + patronId
            );
        }

        return patron.getBorrowingHistory();
    }

    // Get all patrons
    public List<Patron> getAllPatrons() {
        return new ArrayList<>(patrons.values());
    }
}