package com.bookstore.auth;

import com.bookstore.auth.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final IUsersRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            IUsersRepository usersRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {
        this.userRepository = usersRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Grab user from db
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        // Create token with claims
        String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getRole());

        return new AuthResponse(token, user.getUsername(), user.getRole());
    }

    public Boolean register(AuthRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            return false;
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword())); // hash password
        user.setRole(request.getRole());
        userRepository.save(user);

        return true;
    }
}
