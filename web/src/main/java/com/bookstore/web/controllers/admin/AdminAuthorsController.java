package com.bookstore.web.controllers.admin;

import com.bookstore.web.clients.admin.IAuthorsAdminClient;
import com.bookstore.web.external.Author;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/authors")
public class AdminAuthorsController {
    IAuthorsAdminClient authorsAdminClient;

    public AdminAuthorsController(IAuthorsAdminClient authorsAdminClient) {
        this.authorsAdminClient = authorsAdminClient;
    }

    @GetMapping
    public String getAuthorsPage(Model model) {
        List<Author> authors = authorsAdminClient.getAuthors();
        model.addAttribute("authors", authors);

        return "admin/authors/manage-authors";
    }

    @GetMapping("/add-author")
    public String addAuthorPage(Model model) {
        model.addAttribute("author", new Author());

        return "admin/authors/add-author";
    }

    @PostMapping("/add-author")
    public String addAuthor(
        @ModelAttribute Author author,
        @RequestParam("authorImage") MultipartFile imageFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String authorJson = mapper.writeValueAsString(author);

        authorsAdminClient.createAuthor(authorJson, imageFile);

        return "redirect:/admin/authors";
    }

    @GetMapping("/edit-author/{id}")
    public String editAuthorPage(Model model, @PathVariable Long id) {
        Author author = authorsAdminClient.getAuthorById(id);
        model.addAttribute("author", author);

        return "admin/authors/edit-author";
    }

    @PostMapping("/edit-author/{id}")
    public String editAuthor(
        @PathVariable Long id,
        @ModelAttribute Author author,
        @RequestParam("authorImage") MultipartFile imageFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String authorJson = mapper.writeValueAsString(author);

        authorsAdminClient.editAuthor(id, authorJson, imageFile);

        return "redirect:/admin/authors";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        // TODO: Change referential integrity to cascade delete or return "Cannot delete author with existing books"
        authorsAdminClient.deleteAuthor(id);

        return "redirect:/admin/authors";
    }
}
