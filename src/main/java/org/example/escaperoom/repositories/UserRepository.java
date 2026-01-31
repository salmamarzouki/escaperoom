package org.example.escaperoom.repositories;

import org.example.escaperoom.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
