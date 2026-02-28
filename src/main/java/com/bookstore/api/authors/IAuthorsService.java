package com.bookstore.api.authors;

import java.util.List;

public interface IAuthorsService {
    List<Author> findAll();
    Author getAuthorById(Long id);
    void createAuthor(Author author);
    Boolean updateAuthor(Long id, Author author);
    Boolean deleteAuthor(Long id);
}
