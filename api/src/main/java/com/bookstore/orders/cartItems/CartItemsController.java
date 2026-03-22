package com.bookstore.orders.cartItems;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemsController {
    ICartItemsService cartItemsService;

    public CartItemsController(ICartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CartItemDto>> getCartItems() {
        var cartItems = cartItemsService.getCartItems();

        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Integer> addToCart(@RequestBody CartItemDto cartItemDto) {
        var cartQuantity = cartItemsService.addToCart(cartItemDto);

        return new ResponseEntity<>(cartQuantity, HttpStatus.OK);
    }

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> checkout(@RequestBody List<CartItemDto> cartItems) {
        cartItemsService.checkout(cartItems);

        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }

    @GetMapping("/count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Integer> getCartCount() {
        Integer count = cartItemsService.getCartCount();
        return ResponseEntity.ok(count);
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
