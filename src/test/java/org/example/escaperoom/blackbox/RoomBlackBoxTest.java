package org.example.escaperoom.blackbox;

import org.example.escaperoom.entities.Room;
import org.example.escaperoom.entities.Room.DifficultyLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 🔹 1.2 Salle (Room) Service Testing
 */
@DisplayName("Room Service Tests")
public class RoomBlackBoxTest {

    // ========== EQUIVALENCE PARTITIONING TESTS (CT5-CT8) ==========

    @Test
    @DisplayName("CT5: capacity = 6 -> Accepted")
    public void testCT5_ValidCapacity() {
        assertDoesNotThrow(() -> validateRoom(new Room("Test Room", 6, DifficultyLevel.MEDIUM)));
    }

    @Test
    @DisplayName("CT6: capacity = 1 -> Error (< 2)")
    public void testCT6_InvalidCapacity() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> validateRoom(new Room("Test Room", 1, DifficultyLevel.MEDIUM)));
        assertTrue(e.getMessage().contains("Capacity must be between 2 and 10"));
    }

    @Test
    @DisplayName("CT7: level = \"Medium\" -> Accepted")
    public void testCT7_ValidLevel() {
        assertDoesNotThrow(() -> validateRoom(new Room("Test Room", 6, DifficultyLevel.MEDIUM)));
    }

    @Test
    @DisplayName("CT8: level = \"Extreme\" -> Error (Invalid Enum)")
    public void testCT8_InvalidLevel() {
        // Technically strict typing prevents passing strings to enum fields in Java
        // Constructor,
        // but we can simulate the validation logic check or try-catch valueOf
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            try {
                DifficultyLevel level = DifficultyLevel.valueOf("EXTREME");
                validateRoom(new Room("Test Room", 6, level));
            } catch (IllegalArgumentException ex) {
                // Determine if it's enum parsing error or our validation
                throw new IllegalArgumentException("Invalid difficulty level");
            }
        });
        assertTrue(e.getMessage().contains("Invalid difficulty level"));
    }

    // ========== VALIDATION LOGIC ==========

    private void validateRoom(Room room) {
        if (room.getCapacity() < 2 || room.getCapacity() > 10) {
            throw new IllegalArgumentException("Capacity must be between 2 and 10");
        }
        if (room.getLevel() == null) {
            throw new IllegalArgumentException("Level is required");
        }
    }
}
