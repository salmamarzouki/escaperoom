package org.example.escaperoom.services.servicesImpl;

import org.example.escaperoom.entities.Cours;
import org.example.escaperoom.repositories.CoursRepository;
import org.example.escaperoom.services.CoursService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CoursServiceImpl implements CoursService {
    private final CoursRepository coursRepository;

    public CoursServiceImpl(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    @Override
    public List<Cours> getCours() {
        return coursRepository.findAll();
    }

    @Override
    public Cours getCoursById(Long id) {
        return coursRepository.findById(id).orElse(null);
    }

    @Override
    public void createCours(Cours cours) {
        coursRepository.save(cours);
    }

    @Override
    public void updateCours(Cours cours) {
        coursRepository.save(cours);
    }

    @Override
    public void deleteCours(Long id) {
        coursRepository.findById(id).ifPresent(coursRepository::delete);
    }
}
