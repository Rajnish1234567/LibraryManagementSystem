package org.lms.notification.observer;

public class EmailSender implements Observer {

    private final String email;
    public EmailSender(String email) { this.email = email; }
    @Override
    public void update(String message) {
        System.out.println("Email to " + email + ": " + message);
    }
}
