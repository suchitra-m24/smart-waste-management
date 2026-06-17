package com.smartwaste.smart_waste_backend.controller;

import com.smartwaste.smart_waste_backend.model.User;
import com.smartwaste.smart_waste_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    // GET PROFILE
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

    // GET ALL COLLECTORS (ADMIN)
    @GetMapping("/collectors")
    public ResponseEntity<List<User>> getAllCollectors() {
        return ResponseEntity.ok(
                userService.getAllCollectors());
    }

    // GET ALL CITIZENS (ADMIN)
    @GetMapping("/citizens")
    public ResponseEntity<List<User>> getAllCitizens() {
        return ResponseEntity.ok(
                userService.getAllCitizens());
    }

    // GET LEADERBOARD (PUBLIC)
    @GetMapping("/leaderboard")
    public ResponseEntity<List<User>> getLeaderboard() {
        return ResponseEntity.ok(
                userService.getLeaderboard());
    }

    // UPDATE NAME
    @PutMapping("/update-name")
    public ResponseEntity<String> updateName(
            @RequestParam String name,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user.setName(name);
        userService.saveUser(user);
        return ResponseEntity.ok("Name updated successfully!");
    }
}