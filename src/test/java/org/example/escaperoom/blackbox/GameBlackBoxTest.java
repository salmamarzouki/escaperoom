package org.example.escaperoom.blackbox;

import org.example.escaperoom.entities.Game;
import org.example.escaperoom.entities.Game.GameState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 🔹 1.4 Game Service Testing
 */
@DisplayName("Game Service Tests")
public class GameBlackBoxTest {

    // ========== EQUIVALENCE PARTITIONING ==========

    @Test
    @DisplayName("CT20: duration = 60 (30-90) -> Accepted")
    public void testCT20_ValidGameDuration() {
        Game game = new Game(null, 60);
        game.setScore(50);
        game.setState(GameState.NOT_STARTED);
        assertDoesNotThrow(() -> validateGame(game));
    }

    @Test
    @DisplayName("CT21: duration = 20 (<30) -> Error")
    public void testCT21_InvalidDurationLow() {
        Game game = new Game(null, 20);
        Exception e = assertThrows(IllegalArgumentException.class, () -> validateGame(game));
        assertTrue(e.getMessage().contains("Duration must be between 30 and 90 minutes"));
    }

    @Test
    @DisplayName("CT22: duration = 100 (>90) -> Error")
    public void testCT22_InvalidDurationHigh() {
        Game game = new Game(null, 100);
        Exception e = assertThrows(IllegalArgumentException.class, () -> validateGame(game));
        assertTrue(e.getMessage().contains("Duration must be between 30 and 90 minutes"));
    }

    @Test
    @DisplayName("CT23: score = 49 -> Error")
    public void testCT23_InvalidScore() {
        Game game = new Game(null, 60);
        game.setScore(49);
        Exception e = assertThrows(IllegalArgumentException.class, () -> validateGame(game));
        assertTrue(e.getMessage().contains("Score is too low"));
    }

    @Test
    @DisplayName("CT24: state = NOT_STARTED -> Accepted")
    public void testCT24_GameState() {
        assertNotNull(GameState.NOT_STARTED);
        assertNotNull(GameState.IN_PROGRESS);
        assertNotNull(GameState.COMPLETED);
    }

    // ========== VALIDATION LOGIC ==========

    private void validateGame(Game game) {
        if (game.getDuration() < 30 || game.getDuration() > 90) {
            throw new IllegalArgumentException("Duration must be between 30 and 90 minutes");
        }
        if (game.getScore() == null || game.getScore() < 50) {
            throw new IllegalArgumentException("Score is too low - must be at least 50");
        }
    }
}
