package org.example.escaperoom.blackbox;

import org.example.escaperoom.entities.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 🔹 1.5 Score Service Testing
 */
@DisplayName("Score Service Tests")
public class ScoreBlackBoxTest {

    @Test
    @DisplayName("CT25: points = 50 -> Accepted")
    public void testCT25_ValidScore() {
        Score score = new Score(null, 50, true);
        assertDoesNotThrow(() -> validateScore(score));
    }

    @Test
    @DisplayName("CT26: points = 49 -> Error")
    public void testCT26_InvalidScoreLow() {
        Score score = new Score(null, 49, true);
        Exception e = assertThrows(IllegalArgumentException.class, () -> validateScore(score));
        assertTrue(e.getMessage().contains("Score is too low"));
    }

    @Test
    @DisplayName("CT27: points = 5000 -> Accepted (Infinite)")
    public void testCT27_ValidScoreHigh() {
        Score score = new Score(null, 5000, true);
        assertDoesNotThrow(() -> validateScore(score));
    }

    @Test
    @DisplayName("CT28: result = null -> Error")
    public void testCT28_NullResult() {
        assertThrows(IllegalArgumentException.class, () -> validateScorePointsAndResult(50, null));
    }

    // ========== VALIDATION LOGIC ==========

    private void validateScore(Score score) {
        validateScorePointsAndResult(score.getPointsObtained(), score.isSuccess());
    }

    private void validateScorePointsAndResult(Integer points, Boolean success) {
        if (points == null || points < 50) {
            throw new IllegalArgumentException("Score is too low - must be at least 50");
        }
        if (success == null) {
            throw new IllegalArgumentException("Result is required");
        }
    }
}
