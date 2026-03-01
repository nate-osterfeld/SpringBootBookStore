package com.bookstore.api.books;

import java.util.List;

public interface IBooksService {
    List<BookDto> findAll();
    BookDto getBookById(Long id);
    void createBook(BookDto bookDto);
    Boolean updateBook(Long id, BookDto bookDto);
    Boolean deleteBook(Long id);
}
