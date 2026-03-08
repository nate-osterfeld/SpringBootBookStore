package com.bookstore.books;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IBooksService {
    List<BookDto> findAll();
    BookDto getBookById(Long id);
    void createBook(BookDto book, MultipartFile imageFile) throws IOException;
    Boolean updateBook(Long id, BookDto bookDto, MultipartFile imageFile) throws IOException;
    Boolean deleteBook(Long id);
}
