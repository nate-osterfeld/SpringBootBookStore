package com.bookstore.web.controllers;

import com.bookstore.web.clients.IAuthorsClient;
import com.bookstore.web.external.Author;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AuthorsPageController {

    IAuthorsClient authorsClient;

    public AuthorsPageController(IAuthorsClient authorsClient) {
        this.authorsClient = authorsClient;
    }

    @GetMapping("/authors/{id}")
    public String getAuthorDetail(@PathVariable("id") Long id, Model model) {
        Author author = authorsClient.getAuthorById(id);
        model.addAttribute("author", author);
        return "author-detail";
    }
}
