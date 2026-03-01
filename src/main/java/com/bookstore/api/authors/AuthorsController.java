package com.bookstore.api.authors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> createAuthor(@RequestBody Author author) {
        authorsService.createAuthor(author);
        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        var isUpdated = authorsService.updateAuthor(id, author);

        if (!isUpdated) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        // TODO: If author has books, cannot delete...
        // TODO: Change referential integrity to cascade delete or return "Cannot delete author with existing books"

        var isDeleted = authorsService.deleteAuthor(id);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }
}
