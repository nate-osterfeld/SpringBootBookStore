package com.bookstore.orders.cartItems;

import com.bookstore.auth.User;
import com.bookstore.auth.security.CurrentUserService;
import com.bookstore.books.Book;
import com.bookstore.books.IBooksRepository;
import com.bookstore.orders.orders.IOrdersRepository;
import com.bookstore.orders.orders.Order;
import com.bookstore.orders.purchases.IPurchasesRepository;
import com.bookstore.orders.purchases.Purchase;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemsService implements ICartItemsService {
    ICartItemsRepository cartItemsRepository;
    IBooksRepository booksRepository;
    IOrdersRepository ordersRepository;
    IPurchasesRepository purchasesRepository;
    CurrentUserService currentUserService;

    public CartItemsService(
            ICartItemsRepository cartItemsRepository,
            IBooksRepository booksRepository,
            IOrdersRepository ordersRepository,
            IPurchasesRepository purchasesRepository,
            CurrentUserService currentUserService) {
        this.cartItemsRepository = cartItemsRepository;
        this.booksRepository = booksRepository;
        this.ordersRepository = ordersRepository;
        this.purchasesRepository = purchasesRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public List<CartItemDto> getCartItems() {
        var user = currentUserService.getCurrentUser();
        return cartItemsRepository.findCartItemsByUserId(user.getId());
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
    public void deleteCartItem(Long id) {
        var cartItem = cartItemsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart item not found"));
        cartItemsRepository.delete(cartItem);
    }

    @Override
    public int getCartCount() {
        User user = currentUserService.getCurrentUser();
        return cartItemsRepository.sumCartQuantityByUserId(user.getId());
    }

    @Override
    public void checkout(List<CartItemDto> cartItems) {
        // Create new order
        var order = ordersRepository.save(new Order());

        // Save books to purchases (id, orderId, userId, bookId)
        for (var item : cartItems) {
            var purchase = new Purchase();
            purchase.setOrderId(order.getId());
            purchase.setUserId(currentUserService.getCurrentUser().getId());
            purchase.setBookId(item.getBookId());

            Book book = booksRepository.findById(item.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));

            if (book.getQuantity() >= item.getQuantity()) {
                purchase.setQuantity(item.getQuantity());
            } else {
                throw new IllegalStateException("Not enough of '" + book.getTitle() + "' available for the quantity selected.");
            }

            purchasesRepository.save(purchase);
        }

        // Delete books from cart and subtract from stock quantity
        for (var item : cartItems) {
            // Remove cart items from cart
            var cartItemModel = cartItemsRepository.findByBookId(item.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

            cartItemsRepository.delete(cartItemModel);

            // Update quantity of book store
            Book book = booksRepository.findById(cartItemModel.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));

            book.setQuantity(book.getQuantity() - item.getQuantity());

            booksRepository.save(book);
        }
    }
}
