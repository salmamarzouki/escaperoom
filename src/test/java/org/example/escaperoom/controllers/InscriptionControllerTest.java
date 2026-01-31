package org.example.escaperoom.controllers;


import org.example.escaperoom.entities.Inscription;
import org.example.escaperoom.services.InscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InscriptionControllerTest {

    @Mock
    private InscriptionService inscriptionService;

    @InjectMocks
    private InscriptionController inscriptionController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetInscriptions() {
        when(inscriptionService.getInscriptions()).thenReturn(Collections.singletonList(new Inscription()));

        ResponseEntity result = inscriptionController.getInscriptions();

        assertEquals(1, ((java.util.List) result.getBody()).size());
    }

    @Test
    void testGetInscriptionByIdFound() {
        Inscription insc = new Inscription();
        insc.setId(5L);
        when(inscriptionService.getInscriptionById(5L)).thenReturn(insc);

        ResponseEntity<Inscription> result = inscriptionController.getInscriptionById(5L);

        assertNotNull(result.getBody());
    }

    @Test
    void testCreateInscription() {
        Inscription insc = new Inscription();
        ResponseEntity<String> result = inscriptionController.createInscription(insc);

        assertEquals(200, result.getStatusCode().value());
        verify(inscriptionService).createInscription(insc);
    }

    @Test
    void testDeleteInscription() {
        ResponseEntity<String> result = inscriptionController.deleteInscription(10L);

        assertEquals(200, result.getStatusCode().value());
        verify(inscriptionService).deleteInscription(10L);
    }
}
