package com.bookstore.web.clients.admin;

import com.bookstore.web.external.Author;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(
    name="AUTHORS-SERVICE",
    url="http://localhost:5000/api/authors",
    configuration = FeignMultipartConfig.class
)
public interface IAuthorsAdminClient {
    @GetMapping
    List<Author> getAuthors();

    @GetMapping("/{id}")
    Author getAuthorById(@PathVariable Long id);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String createAuthor(
        @RequestPart("author") String authorJson,
        @RequestPart("authorImage") MultipartFile file
    );

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String editAuthor(
        @PathVariable Long id,
        @RequestPart("author") String authorJson,
        @RequestPart("authorImage") MultipartFile file
    );

    @DeleteMapping("/{id}")
    String deleteAuthor(@PathVariable Long id);
}
