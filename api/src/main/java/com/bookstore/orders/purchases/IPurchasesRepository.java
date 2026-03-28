package com.bookstore.orders.purchases;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPurchasesRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserId(Long id);
}
