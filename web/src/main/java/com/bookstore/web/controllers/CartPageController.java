package com.bookstore.web.controllers;

import com.bookstore.web.clients.ICartClient;
import com.bookstore.web.external.CartItem;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartPageController {
    ICartClient cartClient;

    public CartPageController(ICartClient cartClient) {
        this.cartClient = cartClient;
    }

    @GetMapping
    public String getCart(Model model) {
        List<CartItem> cartItems = cartClient.getCartItems();
        BigDecimal subtotal = cartItems.stream()
                .map(item -> item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);

        return "cart";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long id) {
        cartClient.deleteCartItem(id);

        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody List<CartItem> order) {
        cartClient.checkout(order);

        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }
}
