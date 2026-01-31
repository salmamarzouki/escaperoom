package org.example.escaperoom.services;
import org.example.escaperoom.entities.User;
import org.example.escaperoom.repositories.UserRepository;
import org.example.escaperoom.services.servicesImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));

        var result = userService.getUsers();

        assertEquals(1, result.size());
    }

    @Test
    void testGetUserByIdFound() {
        User user = new User();
        user.setId(7L);
        when(userRepository.findById(7L)).thenReturn(Optional.of(user));

        var result = userService.getUserById(7L);

        assertNotNull(result);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setName("salma");

        userService.createUser(user);

        verify(userRepository).save(user);
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setId(8L);
        when(userRepository.findById(8L)).thenReturn(Optional.of(user));

        userService.deleteUser(8L);

        verify(userRepository).delete(user);
    }
}
