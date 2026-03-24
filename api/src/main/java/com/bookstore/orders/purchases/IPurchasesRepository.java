package com.bookstore.orders.purchases;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchasesRepository extends JpaRepository<Purchase, Long> {
}
