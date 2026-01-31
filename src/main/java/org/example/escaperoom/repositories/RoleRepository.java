package org.example.escaperoom.repositories;

import org.example.escaperoom.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
