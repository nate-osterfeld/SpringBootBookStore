package com.bookstore.authors;

import com.bookstore.books.IBooksRepository;
import com.bookstore.config.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorsService implements IAuthorsService {
    IAuthorsRepository authorsRepository;
    IBooksRepository booksRepository;
    FileUploadService fileUploadService;

    public AuthorsService(IAuthorsRepository authorsRepository, IBooksRepository booksRepository, FileUploadService fileUploadService) {
        this.authorsRepository = authorsRepository;
        this.booksRepository = booksRepository;
        this.fileUploadService = fileUploadService;
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
    public void createAuthor(Author author, MultipartFile imageFile) throws IOException {
        String uploadedUrl = fileUploadService.saveUploadedFile(imageFile, "authors");
        if (uploadedUrl != null) {
            author.setAuthorImageUrl(uploadedUrl);
        }

        authorsRepository.save(author);
    }

    @Override
    public Boolean updateAuthor(Long id, Author author, MultipartFile imageFile) throws IOException {
        Optional<Author> a = authorsRepository.findById(id);

        if (a.isEmpty()) { return false; }

        String uploadedUrl = fileUploadService.saveUploadedFile(imageFile, "authors");
        if (uploadedUrl != null) {
            a.get().setAuthorImageUrl(uploadedUrl);
        }

        a.get().setName(author.getName());
        a.get().setBio(author.getBio());

        authorsRepository.save(a.get());

        return true;
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

    // No need for `convertToAuthor` at the moment
    // Not currently accepting dto for create/update
}
