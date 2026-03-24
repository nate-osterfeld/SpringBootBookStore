package com.bookstore.web.external;

public class CartItem {
    private Long id;
    private Book book;
    private int bookId;
    private int quantity;

    public CartItem(Long id, Book book, int quantity, int bookId) {
        this.id = id;
        this.book = book;
        this.quantity = quantity;
        this.bookId = bookId;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBookId() { return bookId; }

    public void setBookId(int bookId) { this.bookId = bookId; }
}
