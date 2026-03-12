package com.bookstore.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String email);
    Boolean existsByUsername(String email);
}
