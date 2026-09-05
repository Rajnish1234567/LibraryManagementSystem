package org.lms.strategy;

import org.lms.entity.Book;
import org.lms.entity.Patron;

import java.util.List;

public interface RecommendationStrategy {
    List<Book> recommend(Patron patron, List<Book> allBooks, int maxResults);
}