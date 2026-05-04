package com.bookstore.web.clients;

import com.bookstore.web.external.Author;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(
        name = "AUTHORS-PUBLIC-SERVICE",
        url = "http://localhost:5000/api/authors"
)
public interface IAuthorsClient {

    @GetMapping
    List<Author> getAuthors();

    @GetMapping("/{id}")
    Author getAuthorById(@PathVariable("id") Long id);
}