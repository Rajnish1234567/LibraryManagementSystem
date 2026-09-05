package org.lms.service;

import org.lms.entity.Book;
import org.lms.entity.Patron;
import org.lms.entity.Reservation;
import org.lms.notification.observer.Observer;
import org.lms.notification.subject.ReservationNotifier;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ReservationService {
    private static final Logger logger = Logger.getLogger(ReservationService.class.getName());
    private final Map<String, Queue<Reservation>> reservations = new ConcurrentHashMap<>(); // ISBN -> queue
    private final ReservationNotifier notifier = new ReservationNotifier();
    private int reservationCounter = 0;

    public void registerObserver(Observer o) {
        notifier.registerObserver(o);
    }

    public Reservation reserve(Book book, Patron patron) {
        if (book.isAvailable()) {
            throw new IllegalStateException("Book is already available");
        }
        String id = "RES-" + (++reservationCounter);
        Reservation res = new Reservation(id, book, patron);
        reservations.computeIfAbsent(book.getIsbn(), k -> new LinkedList<>()).add(res);
        logger.info("Reservation created: " + id + " for " + book.getIsbn());
        return res;
    }

    public void notifyBookAvailable(Book book) {
        Queue<Reservation> queue = reservations.get(book.getIsbn());
        if (queue == null || queue.isEmpty()) return;

        Reservation next = queue.poll();
        next.setFulfilled(true);
        String message = "Book '" + book.getTitle() + "' (ISBN: " + book.getIsbn() +
                ") is now available for " + next.getPatron().getName();
        notifier.notifyObservers(message);
        logger.info("Fulfilled reservation " + next.getId());
    }
}