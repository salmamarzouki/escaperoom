package org.example.escaperoom.controllers;

import org.example.escaperoom.entities.Role;
import org.example.escaperoom.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping("/GetRoles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(service.getRoles());
    }

    @GetMapping("/GetRoleById/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Role role = service.getRoleById(id);
        if (role == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(role);
    }

    @PostMapping("/CreateRole")
    public ResponseEntity<String> createRole(@RequestBody Role role) {
        if (role == null)
            return ResponseEntity.badRequest().build();

        service.createRole(role);
        return ResponseEntity.ok("Role created successfully");
    }

    @PutMapping("/UpdateRole")
    public ResponseEntity<String> updateRole(@RequestBody Role role) {
        if (role == null)
            return ResponseEntity.badRequest().build();

        service.updateRole(role);
        return ResponseEntity.ok("Role updated successfully");
    }

    @DeleteMapping("/DeleteRole/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        service.deleteRole(id);
        return ResponseEntity.ok("Role deleted successfully");
    }
}
