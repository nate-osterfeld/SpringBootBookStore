package com.bookstore.api.books;

import java.util.List;

public interface IBooksService {
    List<Book> findAll();
    Book getBookById(Long id);
    void createBook(Book book);
    Boolean updateBook(Long id, Book book);
    Boolean deleteBook(Long id);
}
