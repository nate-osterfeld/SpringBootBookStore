package com.bookstore.api.books;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> createBook(@RequestBody BookDto bookDto) { // !! book only needs "id" field in "author" object
        booksService.createBook(bookDto);
        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) { // !! book only needs "id" field in "author" object
        var isUpdated = booksService.updateBook(id, bookDto);

        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        var isDeleted = booksService.deleteBook(id);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }
}
