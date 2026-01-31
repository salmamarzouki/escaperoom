package org.example.escaperoom.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Reservation entity for Escape Room Management System
 * Represents a booking made by a client for a specific room
 */
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "number_of_players", nullable = false)
    private Integer numberOfPlayers;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Enum for reservation status
    public enum ReservationStatus {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }

    // Constructors
    public Reservation() {
        this.createdAt = LocalDateTime.now();
        this.status = ReservationStatus.PENDING;
    }

    public Reservation(Client client, Room room, LocalDateTime date, Integer numberOfPlayers) {
        this.client = client;
        this.room = room;
        this.date = date;
        this.numberOfPlayers = numberOfPlayers;
        this.status = ReservationStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    // Business methods for state transitions
    public void confirm() {
        if (this.status != ReservationStatus.PENDING) {
            throw new IllegalStateException("Can only confirm PENDING reservations");
        }
        this.status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status == ReservationStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel COMPLETED reservations");
        }
        if (this.status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already CANCELLED");
        }
        this.status = ReservationStatus.CANCELLED;
    }

    public void complete() {
        if (this.status != ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Can only complete CONFIRMED reservations");
        }
        this.status = ReservationStatus.COMPLETED;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client=" + (client != null ? client.getName() : "null") +
                ", room=" + (room != null ? room.getName() : "null") +
                ", date=" + date +
                ", numberOfPlayers=" + numberOfPlayers +
                ", status=" + status +
                '}';
    }
}
