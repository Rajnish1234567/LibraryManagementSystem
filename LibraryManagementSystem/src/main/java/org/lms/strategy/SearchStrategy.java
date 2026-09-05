package org.lms.strategy;

import org.lms.entity.Book;

import java.util.List;

public interface SearchStrategy {
    List<Book> search(List<Book> inventory, String query);
}