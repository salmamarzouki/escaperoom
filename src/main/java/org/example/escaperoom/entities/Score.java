package org.example.escaperoom.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Score entity for Escape Room Management System
 * Represents the final score and outcome of a game
 */
@Entity
@Table(name = "scores")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "points_obtained", nullable = false)
    private Integer pointsObtained;

    @Column(nullable = false)
    private Boolean success;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public Score() {
        this.createdAt = LocalDateTime.now();
    }

    public Score(Game game, Integer pointsObtained, Boolean success) {
        this.game = game;
        this.pointsObtained = pointsObtained;
        this.success = success;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getPointsObtained() {
        return pointsObtained;
    }

    public void setPointsObtained(Integer pointsObtained) {
        this.pointsObtained = pointsObtained;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", pointsObtained=" + pointsObtained +
                ", success=" + success +
                '}';
    }
}
