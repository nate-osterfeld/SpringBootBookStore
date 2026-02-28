package com.bookstore.api.authors;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorsService implements IAuthorsService {
    IAuthorsRepository authorsRepository;

    public AuthorsService(IAuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorsRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorsRepository.findById(id).orElse(null);
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
}
