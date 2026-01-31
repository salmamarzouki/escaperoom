package org.example.escaperoom.services;

import org.example.escaperoom.entities.Inscription;

import java.util.List;

public interface InscriptionService {
    List<Inscription> getInscriptions();
    Inscription getInscriptionById(Long id);
    void createInscription(Inscription inscription);
    void deleteInscription(Long id);
}
