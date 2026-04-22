package com.bookstore.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
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
        // either remove pdfPath or create new table to hold file path
        return new ResponseEntity<>(booksService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        var book = booksService.getBookById(id);

        if (book == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

      // If wanting signed urls (opting for unique public urls at the moment)
//    @GetMapping("/books/read/{id}")
//    public ResponseEntity<Void> readBook(@PathVariable Long id, Principal principal) {
//        // 1. Logic to check if 'principal.getName()' has bought book 'id'
//         boolean hasPurchased = purchaseService.check(principal.getName(), id);
//
//        // 2. Fetch the path from the DB (e.g., "pdf-content/1776663591855_test.pdf")
//        String pdfPath = booksService.getBookById(id).getPdfPath();
//
//        // 3. Generate the temporary link
//        String signedUrl = fileUploadService.generateSignedUrl(pdfPath);
//
//        // 4. Redirect the user's browser to the PDF
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .location(URI.create(signedUrl))
//                .build();
//    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createBook(
        @RequestPart("book") String bookJson,
        @RequestPart("coverImage") MultipartFile imageFile,
        @RequestPart("bookPdf") MultipartFile pdfFile) throws IOException
    {
        BookDto book = new ObjectMapper().readValue(bookJson, BookDto.class);

        booksService.createBook(book, imageFile, pdfFile);
        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateBook(
            @PathVariable Long id,
            @RequestPart("book") String bookJson,
            @RequestPart("coverImage") MultipartFile imageFile,
            @RequestPart("bookPdf") MultipartFile pdfFile) throws IOException
    {
        BookDto bookDto = new ObjectMapper().readValue(bookJson, BookDto.class);
        var isUpdated = booksService.updateBook(id, bookDto, imageFile, pdfFile);

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
