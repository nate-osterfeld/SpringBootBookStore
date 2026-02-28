package com.bookstore.api.books;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BooksService implements IBooksService {
    IBooksRepository booksRepository;

    public BooksService(IBooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Override
    public void createBook(Book book) {
        booksRepository.save(book);
    }

    @Override
    public Boolean updateBook(Long id, Book book) {
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
}
