package org.example.escaperoom.services;

import org.example.escaperoom.entities.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    Role getRoleById(Long id);
    void createRole(Role role);
    void updateRole(Role role);
    void deleteRole(Long id);

}
