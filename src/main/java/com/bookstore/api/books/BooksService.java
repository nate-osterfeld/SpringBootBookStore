package com.bookstore.api.books;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BooksService implements IBooksService {
    private List<Book> books = new ArrayList<>();
    private static Long id = 3L;

    public BooksService() {
        LoadBooks();
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Book getBookById(Long id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }

        return null;
    }

    @Override
    public void createBook(Book book) {
        book.setId(++id);
        books.add(book);
    }

    @Override
    public Boolean updateBook(Long id, Book book) {
        for (Book b : books) {
            if (b.getId().equals(id)) {
                b.setTitle(book.getTitle());
                b.setAuthor(book.getAuthor());
                b.setGenre(book.getGenre());
                b.setPrice(book.getPrice());
                b.setQuantity(book.getQuantity());
                b.setCoverImageUrl(book.getCoverImageUrl());

                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean deleteBook(Long id) {
        for (int i=0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                books.remove(books.get(i));

                return true;
            }
        }

        return false;
    }

    public void LoadBooks() {
        Book book1 = new Book(
                1L,
                "The Hobbit",
                "J.R.R. Tolkien",
                "Fantasy",
                new BigDecimal("14.99"),
                3,
                "https://example.com/covers/the-hobbit.jpg"
        );

        Book book2 = new Book(
                2L,
                "To Kill a Mockingbird",
                "Harper Lee",
                "Classic",
                new BigDecimal("12.50"),
                5,
                "https://example.com/covers/to-kill-a-mockingbird.jpg"
        );

        Book book3 = new Book(
                3L,
                "1984",
                "George Orwell",
                "Dystopian",
                new BigDecimal("15.00"),
                8,
                "https://example.com/covers/1984.jpg"
        );

        books.add(book1);
        books.add(book2);
        books.add(book3);
    }
}
