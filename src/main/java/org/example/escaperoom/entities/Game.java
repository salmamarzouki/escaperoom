package org.example.escaperoom.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Game entity for Escape Room Management System
 * Represents an active game session
 */
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(nullable = false)
    private Integer duration; // in minutes

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private GameState state;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Enum for game states
    public enum GameState {
        NOT_STARTED, IN_PROGRESS, PAUSED, COMPLETED, FAILED
    }

    // Constructors
    public Game() {
        this.createdAt = LocalDateTime.now();
        this.state = GameState.NOT_STARTED;
        this.score = 0;
    }

    public Game(Reservation reservation, Integer duration) {
        this.reservation = reservation;
        this.duration = duration;
        this.score = 0;
        this.state = GameState.NOT_STARTED;
        this.createdAt = LocalDateTime.now();
    }

    // Business methods for state transitions
    public void start() {
        if (this.state != GameState.NOT_STARTED) {
            throw new IllegalStateException("Can only start games in NOT_STARTED state");
        }
        this.state = GameState.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();
    }

    public void pause() {
        if (this.state != GameState.IN_PROGRESS) {
            throw new IllegalStateException("Can only pause games in IN_PROGRESS state");
        }
        this.state = GameState.PAUSED;
    }

    public void resume() {
        if (this.state != GameState.PAUSED) {
            throw new IllegalStateException("Can only resume games in PAUSED state");
        }
        this.state = GameState.IN_PROGRESS;
    }

    public void complete() {
        if (this.state != GameState.IN_PROGRESS) {
            throw new IllegalStateException("Can only complete games in IN_PROGRESS state");
        }
        this.state = GameState.COMPLETED;
        this.endedAt = LocalDateTime.now();
    }

    public void fail() {
        if (this.state == GameState.NOT_STARTED) {
            throw new IllegalStateException("Cannot fail a game that hasn't started");
        }
        if (this.state == GameState.COMPLETED || this.state == GameState.FAILED) {
            throw new IllegalStateException("Game is already finished");
        }
        this.state = GameState.FAILED;
        this.endedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", duration=" + duration +
                ", score=" + score +
                ", state=" + state +
                '}';
    }
}
