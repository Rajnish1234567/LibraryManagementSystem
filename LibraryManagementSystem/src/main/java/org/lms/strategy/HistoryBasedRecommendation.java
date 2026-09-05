package org.lms.strategy;

import org.lms.entity.Book;
import org.lms.entity.Patron;

import java.util.List;
import java.util.stream.Collectors;

public class HistoryBasedRecommendation implements RecommendationStrategy{
    @Override
    public List<Book> recommend(Patron patron, List<Book> allBooks, int maxResults) {
        List<String> authors = patron.getBorrowingHistory().stream()
                .map(loan -> loan.getBook().getAuthor())
                .toList();

        return allBooks.stream()
                .filter(b -> authors.contains(b.getAuthor()))
                .filter(b -> patron.getBorrowingHistory().stream()
                        .noneMatch(l -> l.getBook().getIsbn().equals(b.getIsbn())))
                .limit(maxResults)
                .collect(Collectors.toList());
    }
}
