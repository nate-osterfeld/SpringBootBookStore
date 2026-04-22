package com.bookstore.web.controllers;

import com.bookstore.web.clients.IBooksClient;
import com.bookstore.web.external.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class BooksPageController {
    IBooksClient booksClient;

    public BooksPageController(IBooksClient booksClient) {
        this.booksClient = booksClient;
    }

    @GetMapping
    public String getBooks(Model model) {
        List<Book> books = booksClient.getBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/{id}")
    public String getBookDetail(@PathVariable Long id, Model model) {
        Book book = booksClient.getBookById(id);
        model.addAttribute("book", book);
        return "book-detail";
    }
}
