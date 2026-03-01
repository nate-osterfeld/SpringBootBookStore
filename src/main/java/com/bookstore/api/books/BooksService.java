package com.bookstore.api.books;

import com.bookstore.api.authors.IAuthorsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public void createBook(BookDto bookdto) {
        var book = convertToBook(bookdto);
        booksRepository.save(book);
    }

    @Override
    public Boolean updateBook(Long id, BookDto bookDto) {
        var book = convertToBook(bookDto);

        Optional<Book> b = booksRepository.findById(id);
        if (b.isPresent()) {
            b.get().setTitle(book.getTitle());
            b.get().setAuthor(book.getAuthor());
            b.get().setDescription(book.getDescription());
            b.get().setGenre(book.getGenre());
            b.get().setPrice(book.getPrice());
            b.get().setQuantity(book.getQuantity());
            b.get().setCoverImageUrl(book.getCoverImageUrl());

            booksRepository.save(b.get());

            return true;
        }

        return false;
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

    public Book convertToBook(BookDto bookDto) {
        var book = new Book();
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
