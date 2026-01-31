package org.example.escaperoom.repositories;

import org.example.escaperoom.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursRepository extends JpaRepository<Cours, Long> {
}
