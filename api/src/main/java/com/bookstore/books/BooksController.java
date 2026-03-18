package com.bookstore.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {
    private final IBooksService booksService;

    public BooksController(IBooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> findAll() {
        return new ResponseEntity<>(booksService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        var book = booksService.getBookById(id);

        if (book == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createBook(
        @RequestPart("book") String bookJson,
        @RequestPart("coverImage") MultipartFile imageFile) throws IOException
    {
        BookDto book = new ObjectMapper().readValue(bookJson, BookDto.class);

        booksService.createBook(book, imageFile);
        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateBook(
            @PathVariable Long id,
            @RequestPart("book") String bookJson,
            @RequestPart("coverImage") MultipartFile imageFile) throws IOException
    {
        BookDto bookDto = new ObjectMapper().readValue(bookJson, BookDto.class);
        var isUpdated = booksService.updateBook(id, bookDto, imageFile);

        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        var isDeleted = booksService.deleteBook(id);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }
}
