package org.lms.strategy;

import org.lms.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorSearchStrategy implements SearchStrategy{

    @Override
    public List<Book> search(List<Book> inventory, String query) {
        String q = query.toLowerCase();
        return inventory.stream()
                .filter(b -> b.getAuthor().toLowerCase().equals(q))
                .collect(Collectors.toList());
    }
}
