package org.example.escaperoom.services;

import org.example.escaperoom.entities.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserById(Long id);
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
}
