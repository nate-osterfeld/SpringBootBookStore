package com.bookstore.books;

import com.bookstore.authors.Author;
import com.bookstore.authors.IAuthorsRepository;
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
public class BooksService implements IBooksService {
    IBooksRepository booksRepository;
    IAuthorsRepository authorsRepository;

    public BooksService(IBooksRepository booksRepository, IAuthorsRepository authorsRepository) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
    }

    @Override
    public List<BookDto> findAll() {
        var books = booksRepository.findAll();

        return books.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        var book = booksRepository.findById(id).orElse(null);

        if (book == null)
            return null;

        return convertToDto(book);
    }

    @Override
    public void createBook(BookDto bookdto, MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            // Get original filename and make it safe
            String originalFilename = imageFile.getOriginalFilename();
            if (originalFilename == null) originalFilename = "image";

            // Replace spaces and special chars
            String safeFilename = originalFilename.replaceAll("[^a-zA-Z0-9.\\-_]", "_");

            // Add UUID prefix to avoid collisions
            String filename = UUID.randomUUID() + "_" + safeFilename;

            // Build absolute upload path relative to project root
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "books", filename);

            // Create parent directories if they don't exist
            Files.createDirectories(uploadPath.getParent());

            // Save file to disk
            imageFile.transferTo(uploadPath.toFile());

            // Store path/URL in entity for DB
            bookdto.setCoverImageUrl("/uploads/books/" + filename);
        }

        var book = convertToBook(bookdto, new Book());
        booksRepository.save(book);
    }

    @Override
    public Boolean updateBook(Long id, BookDto bookDto, MultipartFile imageFile) throws IOException {
        Optional<Author> a = authorsRepository.findById(bookDto.getAuthorId());
        Optional<Book> b = booksRepository.findById(id);

        if (a.isEmpty() || b.isEmpty()) { return false; }

        // If new image isn't provided, keep existing
        if (imageFile.isEmpty()) {
            bookDto.setCoverImageUrl(b.get().getCoverImageUrl());
        } else {
            // Get requested file name
            String requestedFilename = imageFile.getOriginalFilename();
            if (requestedFilename == null) requestedFilename = "image";

            // Replace spaces and special chars
            String safeFilename = requestedFilename.replaceAll("[^a-zA-Z0-9.\\-_]", "_");

            // Add UUID prefix to avoid collisions
            String filename = UUID.randomUUID() + "_" + safeFilename;

            // Build absolute upload path relative to project root
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "books", filename);

            // Create parent directories if they don't exist
            Files.createDirectories(uploadPath.getParent());

            // Save file to disk
            imageFile.transferTo(uploadPath.toFile());

            // Set path/URL
            bookDto.setCoverImageUrl("/uploads/books/" + filename);
        }

        var book = convertToBook(bookDto, b.get());

        booksRepository.save(book);

        return true;
    }

    @Override
    public Boolean deleteBook(Long id) {
        if (!booksRepository.existsById(id)) {
            return false;
        }
        booksRepository.deleteById(id);
        return true;
    }

    public BookDto convertToDto(Book book) {
        var bookDto = new BookDto();

        bookDto.setId(book.getId());
        bookDto.setTitle((book.getTitle()));
        bookDto.setDescription(book.getDescription());
        bookDto.setGenre(book.getGenre());
        bookDto.setPrice(book.getPrice());
        bookDto.setQuantity(book.getQuantity());
        bookDto.setCoverImageUrl(book.getCoverImageUrl());
        bookDto.setAuthorName(book.getAuthor().getName());
        bookDto.setAuthorId(book.getAuthor().getId());

        return bookDto;
    }

    public Book convertToBook(BookDto bookDto, Book book) {
        var author = authorsRepository.findById(bookDto.getAuthorId());

        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setGenre(bookDto.getGenre());
        book.setPrice(bookDto.getPrice());
        book.setQuantity(bookDto.getQuantity());
        book.setCoverImageUrl(bookDto.getCoverImageUrl());
        book.setAuthor(author.orElse(null));

        return book;
    }
}
