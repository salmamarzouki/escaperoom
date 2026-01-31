package org.example.escaperoom.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    public Cours() {
    }

    public Cours(Long id, String titre, String description, LocalDateTime createdAt, LocalDateTime updatedAt, User createdBy) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
