package com.bookstore.web.clients;

import com.bookstore.web.external.Purchase;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="PURCHASES-SERVICE", url="${api.base.url}/api/purchases")
public interface IMyBooksClient {
    @GetMapping
    List<Purchase> getMyBooks();

    @PutMapping("/returns")
    void returnPurchase(@RequestBody Purchase purchase);
}
