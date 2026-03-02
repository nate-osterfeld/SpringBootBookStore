package com.bookstore.authors;

import java.util.List;

public interface IAuthorsService {
    List<AuthorDto> findAll();
    AuthorDto getAuthorById(Long id);
    void createAuthor(Author author);
    Boolean updateAuthor(Long id, Author author);
    Boolean deleteAuthor(Long id);
}
