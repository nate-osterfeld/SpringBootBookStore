package com.bookstore.orders.purchases;

import com.bookstore.books.BookDto;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseDto {
    private BookDto book;
    private Long orderId;
    private int purchaseQuantity;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }
}
