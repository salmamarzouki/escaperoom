package org.example.escaperoom.controllers;

import org.example.escaperoom.entities.Cours;
import org.example.escaperoom.services.CoursService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoursControllerTest {

    @Mock
    private CoursService coursService;

    @InjectMocks
    private CoursController coursController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCours() {
        when(coursService.getCours()).thenReturn(List.of(new Cours(), new Cours()));

        ResponseEntity<List<Cours>> result = coursController.getCours();

        assertEquals(2, result.getBody().size());
    }

    @Test
    void testGetCoursByIdFound() {
        Cours c = new Cours();
        c.setId(1L);
        when(coursService.getCoursById(1L)).thenReturn(c);

        ResponseEntity<Cours> result = coursController.getCoursById(1L);

        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().getId());
    }

    @Test
    void testGetCoursByIdNotFound() {
        when(coursService.getCoursById(99L)).thenReturn(null);

        ResponseEntity<Cours> result = coursController.getCoursById(99L);

        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    void testCreateCours() {
        Cours c = new Cours();
        c.setTitre("Biology");

        ResponseEntity<String> result = coursController.createCours(c);

        assertEquals(200, result.getStatusCode().value());
        verify(coursService).createCours(c);
    }

    @Test
    void testDeleteCours() {
        ResponseEntity<String> result = coursController.deleteCours(1L);

        assertEquals(200, result.getStatusCode().value());
        verify(coursService).deleteCours(1L);
    }
}
