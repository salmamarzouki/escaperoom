package org.example.escaperoom.controllers;

import org.example.escaperoom.entities.User;
import org.example.escaperoom.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        when(userService.getUsers()).thenReturn(Collections.singletonList(new User()));

        ResponseEntity result = userController.getUsers();

        assertEquals(1, ((java.util.List) result.getBody()).size());
    }

    @Test
    void testGetUserByIdFound() {
        User user = new User();
        user.setId(7L);
        when(userService.getUserById(7L)).thenReturn(user);

        ResponseEntity<User> result = userController.getUserById(7L);

        assertNotNull(result.getBody());
        assertEquals(7L, result.getBody().getId());
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setName("Eya");

        ResponseEntity<String> result = userController.createUser(user);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userService).createUser(user);
    }

    @Test
    void testDeleteUser() {
        ResponseEntity<String> result = userController.deleteUser(8L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userService).deleteUser(8L);
    }
}
