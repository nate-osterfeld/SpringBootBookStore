package com.bookstore.orders.cartItems;

import com.bookstore.auth.User;
import com.bookstore.auth.security.CurrentUserService;
import com.bookstore.books.Book;
import com.bookstore.books.IBooksRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemsService implements ICartItemsService {
    ICartItemsRepository cartItemsRepository;
    IBooksRepository booksRepository;
    CurrentUserService currentUserService;

    public CartItemsService(ICartItemsRepository cartItemsRepository, IBooksRepository booksRepository, CurrentUserService currentUserService) {
        this.cartItemsRepository = cartItemsRepository;
        this.booksRepository = booksRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public List<CartItem> getCartItems() {
        var user = currentUserService.getCurrentUser();
        return cartItemsRepository.findByUserId(user.getId());
    }

    @Override
    public int addToCart(CartItemDto cartItemDto) {
        var userId = currentUserService.getCurrentUser().getId();
        var bookId = cartItemDto.getBookId();
        var quantity = cartItemDto.getQuantity();

        CartItem cartItem = cartItemsRepository.findByUserIdAndBookId(userId, bookId)
                .orElse(new CartItem(userId, bookId, 0));

        Book book = booksRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        if (book.getQuantity() - (cartItem.getQuantity() + quantity) < 0)
            throw new IllegalStateException("Max amount of stock has already been placed in cart.");

        if ("ADD".equalsIgnoreCase(cartItemDto.getMode())) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem.setQuantity(quantity);
        }

        cartItemsRepository.save(cartItem);

        return cartItemsRepository.sumCartQuantityByUserId(userId);
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

    @Override
    public int getCartCount() {
        User user = currentUserService.getCurrentUser();
        return cartItemsRepository.sumCartQuantityByUserId(user.getId());
    }
}
