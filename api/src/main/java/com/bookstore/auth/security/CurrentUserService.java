package com.bookstore.auth.security;

import com.bookstore.auth.IUsersRepository;
import com.bookstore.auth.User;
import com.bookstore.auth.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No active security context found");
        }

        Object principal = authentication.getPrincipal();

        // Check if the principal is our custom UserPrincipal
        if (principal instanceof UserPrincipal userPrincipal) {
            // Return the User entity from the principal
            return userPrincipal.getUser();
        }

        throw new IllegalStateException("Expected UserPrincipal but found: " + principal.getClass());
    }
}
