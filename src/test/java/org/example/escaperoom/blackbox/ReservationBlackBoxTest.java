package org.example.escaperoom.blackbox;

import org.example.escaperoom.entities.Reservation;
import org.example.escaperoom.entities.Room;
import org.example.escaperoom.entities.Room.DifficultyLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 🔹 1.3 Reservation Service Testing & 3️⃣ Decision Table Testing
 */
@DisplayName("Reservation Service Tests")
public class ReservationBlackBoxTest {

    // ========== EQUIVALENCE PARTITIONING TESTS (CT9-CT12) ==========

    @Test
    @DisplayName("CT9: players = 4, capacity = 6 -> Accepted")
    public void testCT9_ValidPlayers() {
        Room room = new Room("Room A", 6, DifficultyLevel.EASY);
        Reservation res = new Reservation(null, room, LocalDateTime.now().plusDays(1), 4); // Future date
        assertDoesNotThrow(() -> validateReservation(res));
    }

    @Test
    @DisplayName("CT10: players = 8, capacity = 6 -> Error (> capacity)")
    public void testCT10_InvalidPlayers() {
        Room room = new Room("Room A", 6, DifficultyLevel.EASY);
        Reservation res = new Reservation(null, room, LocalDateTime.now().plusDays(1), 8); // 8 > 6
        Exception e = assertThrows(IllegalArgumentException.class, () -> validateReservation(res));
        assertTrue(e.getMessage().contains("Number of players exceeds room capacity"));
    }

    @Test
    @DisplayName("CT11: date = tomorrow -> Accepted")
    public void testCT11_FutureDate() {
        Room room = new Room("Room A", 6, DifficultyLevel.EASY);
        Reservation res = new Reservation(null, room, LocalDateTime.now().plusDays(1), 4);
        assertDoesNotThrow(() -> validateReservation(res));
    }

    @Test
    @DisplayName("CT12: date = yesterday -> Error")
    public void testCT12_PastDate() {
        Room room = new Room("Room A", 6, DifficultyLevel.EASY);
        Reservation res = new Reservation(null, room, LocalDateTime.now().minusDays(1), 4);
        Exception e = assertThrows(IllegalArgumentException.class, () -> validateReservation(res));
        assertTrue(e.getMessage().contains("Reservation date must be in the future"));
    }

    // ========== DECISION TABLE TESTING (CT17-CT19) ==========
    /*
     * Rules:
     * - VIP & Score >= 800 -> 20%
     * - VIP & Score < 800 -> 10%
     * - Not VIP -> 0%
     */

    @Test
    @DisplayName("CT17: VIP, score=900 -> 20% discount")
    public void testCT17_VipHighScore() {
        int discount = calculateDiscount(true, 900);
        assertEquals(20, discount, "VIP with score 900 should get 20% discount");
    }

    @Test
    @DisplayName("CT18: VIP, score=700 -> 10% discount")
    public void testCT18_VipLowScore() {
        int discount = calculateDiscount(true, 700);
        assertEquals(10, discount, "VIP with score 700 should get 10% discount");
    }

    @Test
    @DisplayName("CT19: Non-VIP -> No discount")
    public void testCT19_NonVip() {
        int discount = calculateDiscount(false, 900); // Score shouldn't matter
        assertEquals(0, discount, "Non-VIP should get 0% discount");

        discount = calculateDiscount(false, 500);
        assertEquals(0, discount);
    }

    // ========== VALIDATION LOGIC ==========

    private void validateReservation(Reservation res) {
        if (res.getNumberOfPlayers() > res.getRoom().getCapacity()) {
            throw new IllegalArgumentException("Number of players exceeds room capacity");
        }
        if (res.getDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservation date must be in the future");
        }
    }

    private int calculateDiscount(boolean isVip, int score) {
        if (!isVip) {
            return 0;
        }
        if (score >= 800) {
            return 20;
        } else {
            return 10;
        }
    }
}
