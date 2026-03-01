package com.bookstore.api.books;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBooksRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorId(Long authorId);
}
