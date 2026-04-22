package com.bookstore.books;

import com.bookstore.authors.Author;
import com.bookstore.authors.IAuthorsRepository;
import com.bookstore.config.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BooksService implements IBooksService {
    IBooksRepository booksRepository;
    IAuthorsRepository authorsRepository;
    FileUploadService fileUploadService;

    public BooksService(IBooksRepository booksRepository, IAuthorsRepository authorsRepository, FileUploadService fileUploadService) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
        this.fileUploadService = fileUploadService;
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
    public void createBook(BookDto bookdto, MultipartFile imageFile, MultipartFile pdfFile) throws IOException {
        String uploadedUrl = fileUploadService.saveUploadedFile(imageFile, "books");
        if (uploadedUrl != null) {
            bookdto.setCoverImageUrl(uploadedUrl);
        }

        String pdfPath = fileUploadService.saveToGcp(pdfFile, "pdf-content");
        if (pdfPath != null) {
            bookdto.setPdfPath(pdfPath);
        }

        var book = convertToBook(bookdto, new Book());
        booksRepository.save(book);
    }

    @Override
    public Boolean updateBook(Long id, BookDto bookDto, MultipartFile imageFile, MultipartFile pdfFile) throws IOException {
        Optional<Author> a = authorsRepository.findById(bookDto.getAuthorId());
        Optional<Book> b = booksRepository.findById(id);

        if (a.isEmpty() || b.isEmpty()) { return false; }

        String uploadedUrl = fileUploadService.saveUploadedFile(imageFile, "books");
        if (uploadedUrl != null) {
            bookDto.setCoverImageUrl(uploadedUrl);
        } else {
            bookDto.setCoverImageUrl(b.get().getCoverImageUrl());
        }

        String pdfPath = fileUploadService.saveToGcp(pdfFile, "pdf-content");
        if (pdfPath != null) {
            bookDto.setPdfPath(pdfPath);
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
        bookDto.setTitle(book.getTitle());
        bookDto.setDescription(book.getDescription());
        bookDto.setGenre(book.getGenre());
        bookDto.setPrice(book.getPrice());
        bookDto.setQuantity(book.getQuantity());
        bookDto.setCoverImageUrl(book.getCoverImageUrl());

        Author author = book.getAuthor();
        if (author != null) {
            bookDto.setAuthorName(author.getName());
            bookDto.setAuthorId(author.getId());
        } else {
            bookDto.setAuthorName("Unknown Author");
            bookDto.setAuthorId(null);
        }

        return bookDto;
    }

    public Book convertToBook(BookDto bookDto, Book book) {
        if (bookDto.getAuthorId() == null) {
            throw new IllegalArgumentException("Author ID must not be null when creating or updating a book.");
        }

        Author author = authorsRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + bookDto.getAuthorId()));

        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setGenre(bookDto.getGenre());
        book.setPrice(bookDto.getPrice());
        book.setQuantity(bookDto.getQuantity());
        book.setCoverImageUrl(bookDto.getCoverImageUrl());
        book.setPdfPath(bookDto.getPdfPath());
        book.setAuthor(author);

        return book;
    }
}
