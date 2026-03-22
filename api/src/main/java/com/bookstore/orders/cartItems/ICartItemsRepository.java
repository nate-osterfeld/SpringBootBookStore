package com.bookstore.orders.cartItems;

import com.bookstore.books.Book;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ICartItemsRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(Long userId);
    Optional<CartItem> findByBookId(Long bookId);
    Optional<CartItem> findByUserIdAndBookId(Long userId, Long bookId);

    @Query("SELECT b FROM Book b JOIN CartItem c ON b.id = c.bookId WHERE c.userId = :userId")
    List<Book> findAllBooksInCartByUserId(@Param("userId") Long userId);

    @Query("SELECT new com.bookstore.orders.cartItems.CartItemDto(b, c.quantity) " +
            "FROM Book b JOIN CartItem c ON b.id = c.bookId " +
            "WHERE c.userId = :userId")
    List<CartItemDto> findCartItemsByUserId(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(c.quantity), 0) FROM CartItem c WHERE c.userId = :userId")
    Integer sumCartQuantityByUserId(Long userId);
}
