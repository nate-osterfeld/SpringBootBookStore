package com.bookstore.web.clients.admin;

import com.bookstore.web.external.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(
        name="BOOKS-SERVICE",
        contextId="booksAdminClient",
        url="http://localhost:5000/api/books",
        configuration = FeignMultipartConfig.class
)
public interface IBooksAdminClient {
    @GetMapping
    List<Book> getBooks();

    @GetMapping("/{id}")
    Book getBookById(@PathVariable Long id);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String createBook(
        @RequestPart("book") String bookJson,
        @RequestPart("coverImage") MultipartFile file
    );

    @DeleteMapping("/{id}")
    String deleteBook(@PathVariable Long id);

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String editBook(
        @PathVariable Long id,
        @RequestPart("book") String bookJson,
        @RequestPart("coverImage") MultipartFile file
    );
}
