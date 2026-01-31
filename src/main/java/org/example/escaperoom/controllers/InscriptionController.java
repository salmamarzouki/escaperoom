package org.example.escaperoom.controllers;

import org.example.escaperoom.entities.Inscription;
import org.example.escaperoom.services.InscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscription")
public class InscriptionController {

    private final InscriptionService service;

    public InscriptionController(InscriptionService service) {
        this.service = service;
    }

    @GetMapping("/GetInscriptions")
    public ResponseEntity<List<Inscription>> getInscriptions() {
        return ResponseEntity.ok(service.getInscriptions());
    }

    @GetMapping("/GetInscriptionById/{id}")
    public ResponseEntity<Inscription> getInscriptionById(@PathVariable Long id) {
        Inscription inscription = service.getInscriptionById(id);
        if (inscription == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(inscription);
    }

    @PostMapping("/CreateInscription")
    public ResponseEntity<String> createInscription(@RequestBody Inscription inscription) {
        if (inscription == null)
            return ResponseEntity.badRequest().build();

        service.createInscription(inscription);
        return ResponseEntity.ok("Inscription created successfully");
    }

    @DeleteMapping("/DeleteInscription/{id}")
    public ResponseEntity<String> deleteInscription(@PathVariable Long id) {
        service.deleteInscription(id);
        return ResponseEntity.ok("Inscription deleted successfully");
    }
}
