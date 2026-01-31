package org.example.escaperoom.repositories;

import org.example.escaperoom.entities.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
}
