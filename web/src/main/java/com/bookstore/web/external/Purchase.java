package com.bookstore.web.external;

public class Purchase {
    private Book book;
    private Long bookId;
    private Long orderId;
    private int purchaseQuantity;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getBookId() { return bookId; }

    public void setBookId(Long bookId) { this.bookId = bookId; }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }
}
