package com.bookstore.orders.cartItems;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemsService implements ICartItemsService {
    ICartItemsRepository cartItemsRepository;

    public CartItemsService(ICartItemsRepository cartItemsRepository) {
        this.cartItemsRepository = cartItemsRepository;
    }

    @Override
    public List<CartItem> getCartByUserId(Long userId) {
        return cartItemsRepository.findByUserId(userId);
    }

    @Override
    public void addToCart(CartItem cartItem) {
        var isItemExists = cartItemsRepository.findByUserIdAndBookId(cartItem.getUserId(), cartItem.getBookId());
        if (isItemExists.isPresent()) {
            var item = isItemExists.get();
            item.setQuantity(item.getQuantity() + 1);
            cartItemsRepository.save(item);
        } else {
            cartItem.setQuantity(1);
            cartItemsRepository.save(cartItem);
        }
    }

    @Override
    public Boolean removeFromCart(Long id) {
        var isItemExists = cartItemsRepository.findById(id);
        if (isItemExists.isPresent()) {
            var item = isItemExists.get();
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                cartItemsRepository.save(item);
            } else if (item.getQuantity() == 1) {
                cartItemsRepository.delete(item);
            }

            return true;
        } else {
            return false;
        }
    }
}
