package org.lms.notification.subject;

import org.lms.notification.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ReservationNotifier implements Subject {
    private static final Logger logger = Logger.getLogger(ReservationNotifier.class.getName());
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        logger.info("Notifying observers: " + message);
        for (Observer o : observers) {
            o.update(message);
        }
    }
}