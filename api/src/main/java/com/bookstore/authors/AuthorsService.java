package com.bookstore.authors;

import com.bookstore.books.IBooksRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public void createAuthor(Author author, MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            // Get original filename and make it safe
            String originalFilename = imageFile.getOriginalFilename();
            if (originalFilename == null) originalFilename = "image";

            // Replace spaces and special chars
            String safeFilename = originalFilename.replaceAll("[^a-zA-Z0-9.\\-_]", "_");

            // Add UUID prefix to avoid collisions
            String filename = UUID.randomUUID() + "_" + safeFilename;

            // Build absolute upload path relative to project root
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "authors", filename);

            // Create parent directories if they don't exist
            Files.createDirectories(uploadPath.getParent());

            // Save file to disk
            imageFile.transferTo(uploadPath.toFile());

            // Store path/URL in entity for DB
            author.setAuthorImageUrl("/uploads/authors/" + filename);
        }

        // 6️⃣ Save author entity
        authorsRepository.save(author);
    }

    @Override
    public Boolean updateAuthor(Long id, Author author, MultipartFile imageFile) throws IOException {
        Optional<Author> a = authorsRepository.findById(id);

        if (a.isEmpty()) { return false; }

        // If new image is provided, update it (otherwise keep existing)
        if (!imageFile.isEmpty()) {
            // Get original filename and make it safe
            String requestedFileName = imageFile.getOriginalFilename();
            if (requestedFileName == null) requestedFileName = "image";

            // Replace spaces and special chars
            String safeFilename = requestedFileName.replaceAll("[^a-zA-Z0-9.\\-_]", "_");

            // Add UUID prefix to avoid collisions
            String filename = UUID.randomUUID() + "_" + safeFilename;

            // Build absolute upload path relative to project root
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "authors", filename);

            // Create parent directories if they don't exist
            Files.createDirectories(uploadPath.getParent());

            // Save file to disk
            imageFile.transferTo(uploadPath.toFile());

            // Store path/URL
            a.get().setAuthorImageUrl("/uploads/authors/" + filename);
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
