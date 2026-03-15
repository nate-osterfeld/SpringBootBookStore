package com.bookstore.auth.security;

import com.bookstore.auth.CustomUserDetailsService;
import com.bookstore.auth.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtFilter(
        JwtService jwtService,
        CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = header.substring(7); // grab token only
        String username = jwtService.extractUsername(token); // extract username from token

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Future update: check if token is valid before hitting db and just use claims instead of object
            UserPrincipal user = userDetailsService.loadUserByUsername(username);

            // create auth object for injection
            UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
                );

            SecurityContextHolder.getContext().setAuthentication(auth); // Inject auth object into security context
        }

        filterChain.doFilter(request,response); // forward middleware
    }
}
