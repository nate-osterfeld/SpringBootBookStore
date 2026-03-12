package com.bookstore.auth.security;

import com.bookstore.auth.IUsersRepository;
import com.bookstore.auth.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final IUsersRepository usersRepository;

    public CurrentUserService(IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return usersRepository.findByUsername(username)
                .orElseThrow();
    }
}
