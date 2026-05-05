package com.bookstore.web.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class SessionSyncController {

    @PostMapping("/session-sync")
    public ResponseEntity<Void> syncToken(@RequestBody Map<String, String> body, HttpSession session) {
        String token = body.get("token");
        if (token != null && token.contains(".")) {
            session.setAttribute("jwt", token);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
