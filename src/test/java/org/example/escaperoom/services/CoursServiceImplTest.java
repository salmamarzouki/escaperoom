package org.example.escaperoom.services;

import org.example.escaperoom.entities.Cours;
import org.example.escaperoom.repositories.CoursRepository;
import org.example.escaperoom.services.servicesImpl.CoursServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoursServiceImplTest {

    @Mock
    private CoursRepository coursRepository;

    @InjectMocks
    private CoursServiceImpl coursService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCours() {
        when(coursRepository.findAll()).thenReturn(List.of(new Cours(), new Cours()));

        var result = coursService.getCours();

        assertEquals(2, result.size());
    }

    @Test
    void testGetCoursByIdFound() {
        Cours c = new Cours();
        c.setId(1L);
        when(coursRepository.findById(1L)).thenReturn(Optional.of(c));

        Cours result = coursService.getCoursById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetCoursByIdNotFound() {
        when(coursRepository.findById(99L)).thenReturn(Optional.empty());

        Cours result = coursService.getCoursById(99L);

        assertNull(result);
    }

    @Test
    void testCreateCours() {
        Cours c = new Cours();
        c.setTitre("Physics");

        coursService.createCours(c);

        verify(coursRepository).save(c);
    }

    @Test
    void testDeleteCours() {
        Cours c = new Cours();
        c.setId(1L);
        when(coursRepository.findById(1L)).thenReturn(Optional.of(c));

        coursService.deleteCours(1L);

        verify(coursRepository).delete(c);
    }
}
