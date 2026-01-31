package org.example.escaperoom.services;

import org.example.escaperoom.entities.Cours;

import java.util.List;

public interface CoursService {
    List<Cours> getCours();
    Cours getCoursById(Long id);
    void createCours(Cours cours);
    void updateCours(Cours cours);
    void deleteCours(Long id);
}
