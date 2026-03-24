package com.bookstore.orders.cartItems;

import com.bookstore.books.Book;

import java.util.List;

public interface ICartItemsService {
    List<CartItemDto> getCartItems();
    int addToCart(CartItemDto cartItemDto);
    int getCartCount();
    void checkout(List<CartItemDto> order);
    void deleteCartItem(Long id);
}
