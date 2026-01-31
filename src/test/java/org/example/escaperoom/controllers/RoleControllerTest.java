package org.example.escaperoom.controllers;

import org.example.escaperoom.entities.Role;
import org.example.escaperoom.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRoles() {
        when(roleService.getRoles()).thenReturn(Collections.singletonList(new Role()));

        ResponseEntity result = roleController.getRoles();

        assertEquals(1, ((java.util.List) result.getBody()).size());
    }

    @Test
    void testGetRoleByIdFound() {
        Role role = new Role();
        role.setId(2L);
        when(roleService.getRoleById(2L)).thenReturn(role);

        ResponseEntity<Role> result = roleController.getRoleById(2L);

        assertNotNull(result.getBody());
        assertEquals(2L, result.getBody().getId());
    }

    @Test
    void testCreateRole() {
        Role role = new Role();
        ResponseEntity<String> result = roleController.createRole(role);

        assertEquals(200, result.getStatusCode().value());
        verify(roleService).createRole(role);
    }

    @Test
    void testDeleteRole() {
        ResponseEntity<String> result = roleController.deleteRole(4L);

        assertEquals(200, result.getStatusCode().value());
        verify(roleService).deleteRole(4L);
    }
}
