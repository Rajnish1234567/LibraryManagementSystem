package org.lms.entity;

import java.util.ArrayList;
import java.util.List;

public class Patron {

    private final int patronId;
    private String name;
    private String email;
    private String phone;

    // Stores borrowing history
    private final List<BorrowingRecord> borrowingHistory;

    public Patron(int patronId, String name, String email, String phone) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.borrowingHistory = new ArrayList<>();
    }

    public int getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<BorrowingRecord> getBorrowingHistory() {
        return borrowingHistory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void addBorrowingRecord(BorrowingRecord record) {
        borrowingHistory.add(record);
    }

    @Override
    public String toString() {
        return "Patron{" +
                "patronId=" + patronId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}