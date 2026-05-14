package application;

import gui.LevelSelectionScreen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests application-level constants and initial screen state.
 */
class MainTest {

    /**
     * Tests that the main game resolution constants stay at the artwork size.
     */
    @Test
    void mainUsesExpectedGameResolution() {
        assertEquals(1920, Main.GAME_WIDTH);
        assertEquals(1080, Main.GAME_HEIGHT);
    }

    /**
     * Tests the default level value before the player chooses a level.
     */
    @Test
    void levelSelectionDefaultsToZeroBeforeUserChoosesLevel() {
        assertEquals(0, LevelSelectionScreen.currentLevel);
    }
}
