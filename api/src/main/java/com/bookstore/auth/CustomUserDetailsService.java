package com.bookstore.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUsersRepository usersRepository;

    public CustomUserDetailsService(IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // Called from JwtFilter, receives username from token, returns user object (injected via auth/security context)
    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserPrincipal(user); // wrap User in UserPrincipal
    }
}
