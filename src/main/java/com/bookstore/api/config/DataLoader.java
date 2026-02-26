package com.bookstore.api.config;

import com.bookstore.api.books.Book;
import com.bookstore.api.books.IBooksRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final IBooksRepository booksRepository;

    public DataLoader(IBooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public void run(String... args) {
        booksRepository.save(new Book(null, "The Hobbit", "J.R.R. Tolkien", "Fantasy",
                new BigDecimal("14.99"), 3, "https://example.com/covers/the-hobbit.jpg"));

        booksRepository.save(new Book(null, "To Kill a Mockingbird", "Harper Lee", "Classic",
                new BigDecimal("12.50"), 5, "https://example.com/covers/to-kill-a-mockingbird.jpg"));

        booksRepository.save(new Book(null, "1984", "George Orwell", "Dystopian",
                new BigDecimal("15.00"), 8, "https://example.com/covers/1984.jpg"));
    }
}
