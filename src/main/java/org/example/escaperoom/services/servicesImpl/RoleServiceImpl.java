package org.example.escaperoom.services.servicesImpl;

import org.example.escaperoom.entities.Role;
import org.example.escaperoom.repositories.RoleRepository;
import org.example.escaperoom.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void createRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void updateRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.findById(id).ifPresent(roleRepository::delete);
    }
}