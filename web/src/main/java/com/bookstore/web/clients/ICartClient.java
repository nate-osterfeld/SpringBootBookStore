package com.bookstore.web.clients;

import com.bookstore.web.external.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="CART-SERVICE", url="http://localhost:5000/api/cart")
public interface ICartClient {
    @GetMapping
    List<CartItem> getCartItems();

    @PostMapping(value = "/checkout", consumes = "application/json")
    String checkout(@RequestBody List<CartItem> order);

    @DeleteMapping("/delete/{id}")
    String deleteCartItem(@PathVariable Long id);
}
