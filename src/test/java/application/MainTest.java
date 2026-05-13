package application;

import gui.LevelSelectionScreen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void mainUsesExpectedGameResolution() {
        assertEquals(1920, Main.GAME_WIDTH);
        assertEquals(1080, Main.GAME_HEIGHT);
    }

    @Test
    void levelSelectionDefaultsToZeroBeforeUserChoosesLevel() {
        assertEquals(0, LevelSelectionScreen.currentLevel);
    }
}
