package com.bookstore.authors;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorsRepository extends JpaRepository<Author, Long> {
}