package com.bookstore.web.controllers;

import com.bookstore.web.clients.IMyBooksClient;
import com.bookstore.web.external.Purchase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/purchases")
public class MyBooksPageController {
    IMyBooksClient myBooksClient;

    public MyBooksPageController(IMyBooksClient myBooksClient) {
        this.myBooksClient = myBooksClient;
    }

    @GetMapping
    public String getPurchases(Model model) {
        List<Purchase> purchases = myBooksClient.getMyBooks();
        model.addAttribute("purchases", purchases);
        return "my-books";
    }

    @PostMapping("/returns")
    public ResponseEntity<String> returnPurchase(@RequestBody Purchase purchase) {
        myBooksClient.returnPurchase(purchase);
        return new ResponseEntity<>("Status: success", HttpStatus.OK);
    }
}
