package com.bookstore.orders.cartItems;

import com.bookstore.books.Book;

public class CartItemDto {
    private Long id;
    private Long bookId;
    private Book book;
    private int quantity;
    private String mode;

    public CartItemDto(Long id, Book book, int quantity) {
        this.id = id;
        this.book = book;
        this.quantity = quantity;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Book getBook() { return book; }

    public void setBook(Book book) { this.book = book; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
