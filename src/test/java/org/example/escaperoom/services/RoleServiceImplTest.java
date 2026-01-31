package org.example.escaperoom.services;

import org.example.escaperoom.entities.Role;
import org.example.escaperoom.repositories.RoleRepository;
import org.example.escaperoom.services.servicesImpl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRoles() {
        when(roleRepository.findAll()).thenReturn(Collections.singletonList(new Role()));

        var result = roleService.getRoles();

        assertEquals(1, result.size());
    }

    @Test
    void testGetRoleByIdFound() {
        Role role = new Role();
        role.setId(3L);

        when(roleRepository.findById(3L)).thenReturn(Optional.of(role));

        var result = roleService.getRoleById(3L);

        assertNotNull(result);
    }

    @Test
    void testCreateRole() {
        Role role = new Role();
        role.setName("Admin");

        roleService.createRole(role);

        verify(roleRepository).save(role);
    }

    @Test
    void testDeleteRole() {
        Role role = new Role();
        role.setId(4L);
        when(roleRepository.findById(4L)).thenReturn(Optional.of(role));

        roleService.deleteRole(4L);

        verify(roleRepository).delete(role);
    }
}
