package com.bookstore.api.books;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBooksRepository extends JpaRepository<Book, Long> {
}
