package org.lms.notification.observer;

public class TextSender implements Observer {

    private final String mobile;

    public TextSender(String mobile) {
        this.mobile = mobile;
    }
    @Override
    public void update(String message) {

    }
}
