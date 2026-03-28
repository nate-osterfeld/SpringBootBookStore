package com.bookstore.orders.purchases;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/returns")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> returnPurchase(@RequestBody Purchase purchase) {
        Boolean result = purchasesService.returnPurchase(purchase);

        if (result == true) {
            return new ResponseEntity<>("Status: success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Status: failed", HttpStatus.BAD_REQUEST);
        }
    }
}
