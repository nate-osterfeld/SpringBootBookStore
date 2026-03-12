package com.bookstore.auth;

import com.bookstore.auth.security.CurrentUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class TestController {
    private final CurrentUserService currentUserService;

    public TestController(IUsersRepository usersRepository, CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @GetMapping("/test")
    public String permitAll() {
        return "anyone can access";
    }

    @GetMapping("/test/auth")
    public String authenticated() {
        User user = currentUserService.getCurrentUser();
        return "you are logged in as " + user.getUsername() + " with role of " + user.getRole();
    }

    @GetMapping("/test/admin")
    public String adminOnly() {
        User user = currentUserService.getCurrentUser();
        return "you are a logged in as " + user.getUsername() + " with role of " + user.getRole();
    }
}
