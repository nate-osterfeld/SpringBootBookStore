package com.bookstore.orders.orders;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdersRepository extends JpaRepository<Order, Long> {
}
