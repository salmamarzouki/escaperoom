package org.example.escaperoom.controllers;

import org.example.escaperoom.entities.User;
import org.example.escaperoom.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/GetUsers")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(service.getUsers());
    }

    @GetMapping("/GetUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = service.getUserById(id);
        if (user == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(user);
    }

    @PostMapping("/CreateUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (user == null)
            return ResponseEntity.badRequest().build();

        service.createUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @PutMapping("/UpdateUser")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        if (user == null)
            return ResponseEntity.badRequest().build();

        service.updateUser(user);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/DeleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
