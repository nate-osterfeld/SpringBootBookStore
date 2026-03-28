package com.bookstore.orders.purchases;

import com.bookstore.auth.security.CurrentUserService;
import com.bookstore.authors.Author;
import com.bookstore.books.Book;
import com.bookstore.books.BookDto;
import com.bookstore.books.IBooksRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PurchasesService implements IPurchasesService{
    IPurchasesRepository purchasesRepository;
    IBooksRepository booksRepository;
    CurrentUserService currentUserService;

    public PurchasesService(IPurchasesRepository purchasesRepository, IBooksRepository booksRepository, CurrentUserService currentUserService) {
        this.purchasesRepository = purchasesRepository;
        this.booksRepository = booksRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public List<PurchaseDto> getPurchasesByUserId() {
        // Get current user
        var user = currentUserService.getCurrentUser();
        // Get purchases for current user
        var purchases = purchasesRepository.findByUserId(user.getId());

        // Create list of bookIds from purchases
        List<Long> bookIds = new ArrayList<>();
        for (Purchase p : purchases) {
            bookIds.add(p.getBookId());
        }

        // Get all Books from database for bookIds
        List<Book> allBooks = booksRepository.findAllById(bookIds);

        // Create hashmap of books from allBooks for quick lookup
        Map<Long, Book> bookMap = new HashMap<>();
        for (Book book : allBooks) {
            bookMap.put(book.getId(), book);
        }

        // Create list of purchaseDto's to return
        List<PurchaseDto> results = new ArrayList<>();
        for (Purchase p : purchases) {
            Book book = bookMap.get(p.getBookId());
            if (book != null) {
                PurchaseDto purchaseDto = new PurchaseDto();
                purchaseDto.setBook(convertToDto(book));
                purchaseDto.setOrderId(p.getOrderId());
                purchaseDto.setPurchaseQuantity(p.getQuantity());
                results.add(purchaseDto);
            }
        }

        return results;
    }

    @Override
    public Boolean returnPurchase(Purchase purchase) {
        var user = currentUserService.getCurrentUser();
        var purchases = purchasesRepository.findByUserId(user.getId());

        // Loop through user's purchases looking for matching bookId and orderId (dec quantity or delete if 1 left)
        for (Purchase p : purchases) {
            if (Objects.equals(p.getBookId(), purchase.getBookId()) && Objects.equals(p.getOrderId(), purchase.getOrderId())) {
                if (p.getQuantity() > 1) {
                    p.setQuantity(p.getQuantity() - 1);

                    purchasesRepository.save(p);
                } else {
                    purchasesRepository.delete(p);
                }

                return true;
            }
        }

        return false;
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
}
