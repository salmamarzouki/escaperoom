package org.example.escaperoom.services.servicesImpl;

import org.example.escaperoom.entities.Inscription;
import org.example.escaperoom.repositories.InscriptionRepository;
import org.example.escaperoom.services.InscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InscriptionServiceImpl implements InscriptionService {
    private final InscriptionRepository inscriptionRepository;

    public InscriptionServiceImpl(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    @Override
    public List<Inscription> getInscriptions() {
        return inscriptionRepository.findAll();
    }

    @Override
    public Inscription getInscriptionById(Long id) {
        return inscriptionRepository.findById(id).orElse(null);
    }

    @Override
    public void createInscription(Inscription inscription) {
        inscriptionRepository.save(inscription);
    }

    @Override
    public void deleteInscription(Long id) {
        inscriptionRepository.findById(id).ifPresent(inscriptionRepository::delete);
    }
}
