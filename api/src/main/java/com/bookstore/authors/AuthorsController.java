package com.bookstore.authors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorsController {
    private final IAuthorsService authorsService;

    public AuthorsController(IAuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> findAll() {
        return new ResponseEntity<>(authorsService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getBookById(@PathVariable Long id) {
        var author = authorsService.getAuthorById(id);

        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createAuthor(
        @RequestPart("author") String authorJson,
        @RequestPart("authorImage") MultipartFile imageFile) throws IOException
    {
        Author author = new ObjectMapper().readValue(authorJson, Author.class);
        authorsService.createAuthor(author, imageFile);
        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateAuthor(
            @PathVariable Long id,
            @RequestPart("author") String authorJson,
            @RequestPart("authorImage") MultipartFile imageFile) throws IOException
    {
        Author author = new ObjectMapper().readValue(authorJson, Author.class);
        var isUpdated = authorsService.updateAuthor(id, author, imageFile);

        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        // TODO: Change referential integrity to cascade delete or return "Cannot delete author with existing books"

        var isDeleted = authorsService.deleteAuthor(id);

        if (!isDeleted) {
            return new ResponseEntity<>("Status: failed", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }
}
