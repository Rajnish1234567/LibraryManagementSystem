package org.lms.factory;

import org.lms.entity.Patron;
import org.lms.exception.MissingArgumentException;

public class PatronFactory {
    public static Patron createPatron(String id, String name, String email) {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new MissingArgumentException("ID and name are required");
        }
        return new Patron(id, name, email);
    }
}