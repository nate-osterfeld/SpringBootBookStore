package com.bookstore.orders.purchases;

import com.bookstore.orders.cartItems.CartItemDto;
import com.bookstore.orders.cartItems.ICartItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchasesController {
    IPurchasesService purchasesService;

    public PurchasesController(IPurchasesService purchasesService) {
        this.purchasesService = purchasesService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PurchaseDto>> getPurchasesByUserId() {
        var purchases = purchasesService.getPurchasesByUserId();

        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }
}
