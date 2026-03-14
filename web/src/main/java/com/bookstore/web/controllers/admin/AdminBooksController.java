package com.bookstore.web.controllers.admin;

import com.bookstore.web.clients.admin.IAuthorsAdminClient;
import com.bookstore.web.clients.admin.IBooksAdminClient;
import com.bookstore.web.external.Author;
import com.bookstore.web.external.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/books")
public class AdminBooksController {
    private final IBooksAdminClient booksAdminClient;
    private final IAuthorsAdminClient authorsAdminClient;
    private final ObjectMapper objectMapper;

    public AdminBooksController(IBooksAdminClient booksAdminClient,
                                IAuthorsAdminClient authorsAdminClient,
                                ObjectMapper objectMapper) {
        this.booksAdminClient = booksAdminClient;
        this.authorsAdminClient = authorsAdminClient;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public String getBooksPage(Model model) {
        List<Book> books = booksAdminClient.getBooks();
        model.addAttribute("books", books);

        return "admin/books/manage-books";
    }

    @GetMapping("/add-book")
    public String addBookPage(Model model) {
        List<Author> authors = authorsAdminClient.getAuthors();
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authors);

        return "admin/books/add-book";
    }

    @PostMapping("/add-book")
    public String addBook(
        @ModelAttribute Book book,
        @RequestParam("coverImage") MultipartFile imageFile) throws IOException {
        String bookJson = objectMapper.writeValueAsString(book);
        booksAdminClient.createBook(bookJson, imageFile);
        return "redirect:/admin/books";
    }

    @GetMapping("/edit-book/{id}")
    public String editBookPage(Model model, @PathVariable Long id) {
        Book book = booksAdminClient.getBookById(id);
        List<Author> authors = authorsAdminClient.getAuthors();
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);

        return "admin/books/edit-book";
    }

    @PostMapping("/edit-book/{id}")
    public String editBook(
        @PathVariable Long id,
        @ModelAttribute Book book,
        @RequestParam("coverImage") MultipartFile imageFile) throws IOException {
        String bookJson = objectMapper.writeValueAsString(book);
        booksAdminClient.editBook(id, bookJson, imageFile);
        return "redirect:/admin/books";
    }

        @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        booksAdminClient.deleteBook(id);
        return "redirect:/admin/books";
    }
}
