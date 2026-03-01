package com.bookstore.api.authors;

import com.bookstore.api.books.Book;
import com.bookstore.api.books.BookDto;
import com.bookstore.api.books.IBooksRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorsService implements IAuthorsService {
    IAuthorsRepository authorsRepository;
    IBooksRepository booksRepository;

    public AuthorsService(IAuthorsRepository authorsRepository, IBooksRepository booksRepository) {
        this.authorsRepository = authorsRepository;
        this.booksRepository = booksRepository;
    }

    @Override
    public List<AuthorDto> findAll() {
        var authors = authorsRepository.findAll();

        return authors.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public AuthorDto getAuthorById(Long id) {
        var author = authorsRepository.findById(id).orElse(null);

        if (author == null)
            return null;

        return convertToDto(author);
    }

    @Override
    public void createAuthor(Author author) {
        authorsRepository.save(author);
    }

    @Override
    public Boolean updateAuthor(Long id, Author author) {
        Optional<Author> a = authorsRepository.findById(id);
        if (a.isPresent()) {
            a.get().setName(author.getName());
            a.get().setBio(author.getBio());
            a.get().setAuthorImageUrl(author.getAuthorImageUrl());

            authorsRepository.save(a.get());

            return true;
        }

        return false;
    }

    @Override
    public Boolean deleteAuthor(Long id) {
        if (!authorsRepository.existsById(id)) {
            return false;
        }
        authorsRepository.deleteById(id);
        return true;
    }

    public AuthorDto convertToDto(Author author) {
        var authorDto = new AuthorDto();
        var books = booksRepository.findByAuthorId(author.getId());

        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setBio(author.getBio());
        authorDto.setAuthorImageUrl(author.getAuthorImageUrl());
        authorDto.setBooks(books);

        return authorDto;
    }
}
