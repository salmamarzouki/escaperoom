package org.example.escaperoom.services;

import org.example.escaperoom.entities.Inscription;
import org.example.escaperoom.repositories.InscriptionRepository;
import org.example.escaperoom.services.servicesImpl.InscriptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InscriptionServiceImplTest {

    @Mock
    private InscriptionRepository inscriptionRepository;

    @InjectMocks
    private InscriptionServiceImpl inscriptionService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllInscriptions() {
        when(inscriptionRepository.findAll()).thenReturn(Collections.singletonList(new Inscription()));

        var result = inscriptionService.getInscriptions();

        assertEquals(1, result.size());
    }

    @Test
    void testGetInscriptionByIdFound() {
        Inscription insc = new Inscription();
        insc.setId(5L);
        when(inscriptionRepository.findById(5L)).thenReturn(Optional.of(insc));

        var result = inscriptionService.getInscriptionById(5L);

        assertNotNull(result);
    }

    @Test
    void testCreateInscription() {
        Inscription insc = new Inscription();

        inscriptionService.createInscription(insc);

        verify(inscriptionRepository).save(insc);
    }

    @Test
    void testDeleteInscription() {
        Inscription insc = new Inscription();
        insc.setId(10L);
        when(inscriptionRepository.findById(10L)).thenReturn(Optional.of(insc));

        inscriptionService.deleteInscription(10L);

        verify(inscriptionRepository).delete(insc);
    }
}
