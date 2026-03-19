package com.bookstore.orders.cartItems;

import java.util.List;

public interface ICartItemsService {
    List<CartItem> getCartItems();
    int addToCart(CartItemDto cartItemDto);
    Boolean removeFromCart(Long id);
    int getCartCount();
}
