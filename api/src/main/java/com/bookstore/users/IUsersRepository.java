package com.bookstore.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersRepository extends JpaRepository<User, Long> {
}
