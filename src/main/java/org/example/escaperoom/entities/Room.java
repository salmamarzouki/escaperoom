package org.example.escaperoom.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Room entity for Escape Room Management System
 * Represents an escape room with its properties
 */
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private DifficultyLevel level;

    @Column(nullable = false)
    private Boolean availability;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Enum for difficulty levels
    public enum DifficultyLevel {
        EASY, MEDIUM, HARD
    }

    // Constructors
    public Room() {
        this.createdAt = LocalDateTime.now();
        this.availability = true;
    }

    public Room(String name, Integer capacity, DifficultyLevel level) {
        this(name, capacity, level, true);
    }

    public Room(String name, Integer capacity, DifficultyLevel level, Boolean availability) {
        this.name = name;
        this.capacity = capacity;
        this.level = level;
        this.availability = availability;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public DifficultyLevel getLevel() {
        return level;
    }

    public void setLevel(DifficultyLevel level) {
        this.level = level;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", level=" + level +
                ", availability=" + availability +
                '}';
    }
}
