package com.bookstore.web.clients;

import com.bookstore.web.external.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="BOOKS-SERVICE", url="http://localhost:5000/api/books")
public interface IBooksClient {
    @GetMapping
    List<Book> getBooks();

    @GetMapping("/{id}")
    Book getBookById(@PathVariable("id") Long id);
}
