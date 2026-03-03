package com.bookstore.orders.cartItems;

import java.util.List;

public interface ICartItemsService {
    List<CartItem> getCartByUserId(Long userId);
    void addToCart(CartItem cartItem);
    Boolean removeFromCart(Long id);
}
