package com.bookstore.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @RequestBody AuthRequest request,
        HttpServletResponse response
    ) {
        String token = authService.login(request);

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); // 1 day

        response.addCookie(cookie);

        return ResponseEntity.ok(new AuthResponse(token, request.getUsername()));
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        var result = authService.register(request);
        if (result == false) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication authentication) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal(); // Grab user principal from auth object

        return ResponseEntity.ok(Map.of(
            "id", user.getId(),
            "username", user.getUsername(),
            "roles", user.getAuthorities()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // delete cookie

        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }
}
