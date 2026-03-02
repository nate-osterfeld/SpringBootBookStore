package com.bookstore.orders.cartItems;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemsController {
    ICartItemsService cartItemsService;

    public CartItemsController(ICartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCartByUserId(@PathVariable Long userId) {
        var cartItems = cartItemsService.getCartByUserId(userId);

        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody CartItem cartItem) {
        cartItemsService.addToCart(cartItem);
        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long id) {
        var isExists = cartItemsService.removeFromCart(id);

        if (!isExists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }
}
