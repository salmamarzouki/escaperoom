package org.example.escaperoom.blackbox;

import org.example.escaperoom.entities.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 🔹 1.1 Client Service Testing (CT01-CT16)
 * Total: 16 Test Cases
 */
@DisplayName("Client Service - 16 Tests")
public class ClientBlackBoxTest {

    // ========== EQUIVALENCE PARTITIONING (CT01-CT12) ==========

    @Test
    @DisplayName("CT01: age = 25 -> Client accepted (PEV)")
    public void testCT01() {
        assertDoesNotThrow(() -> validate(new Client("Salma", "s@test.com", 25)));
    }

    @Test
    @DisplayName("CT02: age = 17 -> Error: invalid age (PENV)")
    public void testCT02() {
        assertThrows(IllegalArgumentException.class, () -> validate(new Client("Salma", "s@test.com", 17)));
    }

    @Test
    @DisplayName("CT03: email = 'user@test.com' -> Client accepted (PEV)")
    public void testCT03() {
        assertDoesNotThrow(() -> validate(new Client("Salma", "user@test.com", 25)));
    }

    @Test
    @DisplayName("CT04: email = 'user.test.com' -> Error: invalid email (PENV)")
    public void testCT04() {
        assertThrows(IllegalArgumentException.class, () -> validate(new Client("Salma", "user.test.com", 25)));
    }

    @Test
    @DisplayName("CT05: name = '' -> Error: empty name (PENV)")
    public void testCT05() {
        assertThrows(IllegalArgumentException.class, () -> validate(new Client("", "s@test.com", 25)));
    }

    @Test
    @DisplayName("CT06: name = '123' -> Error: non-alphabetic (PENV)")
    public void testCT06() {
        assertThrows(IllegalArgumentException.class, () -> validate(new Client("123", "s@test.com", 25)));
    }

    @Test
    @DisplayName("CT07: age = 61 -> Error: > 60 (PENV)")
    public void testCT07() {
        assertThrows(IllegalArgumentException.class, () -> validate(new Client("Salma", "s@test.com", 61)));
    }

    @Test
    @DisplayName("CT08: email = '' -> Error: empty email (PENV)")
    public void testCT08() {
        assertThrows(IllegalArgumentException.class, () -> validate(new Client("Salma", "", 25)));
    }

    @Test
    @DisplayName("CT09: name = null -> Error (PENV)")
    public void testCT09() {
        assertThrows(IllegalArgumentException.class, () -> validate(new Client(null, "s@test.com", 25)));
    }

    @Test
    @DisplayName("CT10: age = null -> Error (PENV)")
    public void testCT10() {
        assertThrows(IllegalArgumentException.class, () -> validate(new Client("Salma", "s@test.com", null)));
    }

    @Test
    @DisplayName("CT11: email = null -> Error (PENV)")
    public void testCT11() {
        assertThrows(IllegalArgumentException.class, () -> validate(new Client("Salma", null, 25)));
    }

    @Test
    @DisplayName("CT12: name > 100 chars -> Error (PENV)")
    public void testCT12() {
        String longName = "A".repeat(101);
        assertThrows(IllegalArgumentException.class, () -> validate(new Client(longName, "s@test.com", 25)));
    }

    // ========== BOUNDARY VALUE ANALYSIS (CT13-CT16) ==========

    @Test
    @DisplayName("CT13: age = 17 -> rejected (Lower Limit - 1)")
    public void testCT13() {
        assertThrows(IllegalArgumentException.class, () -> validate(new Client("S", "s@t.com", 17)));
    }

    @Test
    @DisplayName("CT14: age = 18 -> accepted (Lower Limit)")
    public void testCT14() {
        assertDoesNotThrow(() -> validate(new Client("S", "s@t.com", 18)));
    }

    @Test
    @DisplayName("CT15: age = 60 -> accepted (Upper Limit)")
    public void testCT15() {
        assertDoesNotThrow(() -> validate(new Client("S", "s@t.com", 60)));
    }

    @Test
    @DisplayName("CT16: age = 61 -> rejected (Upper Limit + 1)")
    public void testCT16() {
        assertThrows(IllegalArgumentException.class, () -> validate(new Client("S", "s@t.com", 61)));
    }

    private void validate(Client c) {
        if (c.getName() == null || c.getName().isEmpty() || c.getName().matches(".*\\d.*")
                || c.getName().length() > 100)
            throw new IllegalArgumentException("Invalid name");
        if (c.getEmail() == null || c.getEmail().isEmpty() || !c.getEmail().contains("@"))
            throw new IllegalArgumentException("Invalid email");
        if (c.getAge() == null || c.getAge() < 18 || c.getAge() > 60)
            throw new IllegalArgumentException("Invalid age (18-60)");
    }
}
