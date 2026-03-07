package com.bookstore.authors;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IAuthorsService {
    List<AuthorDto> findAll();
    AuthorDto getAuthorById(Long id);
    void createAuthor(Author author, MultipartFile imageFile) throws IOException;
    Boolean updateAuthor(Long id, Author author, MultipartFile imageFile) throws IOException;
    Boolean deleteAuthor(Long id);
}
