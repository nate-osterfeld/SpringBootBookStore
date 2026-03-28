package com.bookstore.web.clients;

import com.bookstore.web.external.CartItem;
import com.bookstore.web.external.Purchase;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="PURCHASES-SERVICE", url="http://localhost:5000/api/purchases")
public interface IMyBooksClient {
    @GetMapping
    List<Purchase> getMyBooks();
}
