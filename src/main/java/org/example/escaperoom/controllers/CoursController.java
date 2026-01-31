package org.example.escaperoom.controllers;



import org.example.escaperoom.entities.Cours;
import org.example.escaperoom.services.CoursService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {

    private final CoursService service;

    public CoursController(CoursService service) {
        this.service = service;
    }

    @GetMapping("/GetCours")
    public ResponseEntity<List<Cours>> getCours() {
        return ResponseEntity.ok(service.getCours());
    }

    @GetMapping("/GetCoursById/{id}")
    public ResponseEntity<Cours> getCoursById(@PathVariable Long id) {
        Cours cours = service.getCoursById(id);
        if (cours == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cours);
    }

    @PostMapping("/CreateCours")
    public ResponseEntity<String> createCours(@RequestBody Cours cours) {
        if (cours == null)
            return ResponseEntity.badRequest().build();

        service.createCours(cours);
        return ResponseEntity.ok("Cours created successfully");
    }

    @PutMapping("/UpdateCours")
    public ResponseEntity<String> updateCours(@RequestBody Cours cours) {
        if (cours == null)
            return ResponseEntity.badRequest().build();

        service.updateCours(cours);
        return ResponseEntity.ok("Cours updated successfully");
    }

    @DeleteMapping("/DeleteCours/{id}")
    public ResponseEntity<String> deleteCours(@PathVariable Long id) {
        service.deleteCours(id);
        return ResponseEntity.ok("Cours deleted successfully");
    }
}
