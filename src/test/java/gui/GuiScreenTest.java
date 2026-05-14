package gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import testsupport.FxTestSupport;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests JavaFX screen construction and basic layout behavior.
 */
class GuiScreenTest {

    /**
     * Tests that the main menu constructor builds its background and buttons.
     */
    @Test
    @Timeout(10)
    void mainMenuScreenBuildsBackgroundAndNavigationButtons() {
        FxTestSupport.runOnFxThread(() -> {
            MainMenuScreen screen = new MainMenuScreen(null);

            assertEquals(4, screen.getChildren().size());
            assertEquals(1, countChildrenOfType(screen, ImageView.class));
            assertEquals(3, countChildrenOfType(screen, Button.class));
            assertAllButtonsHidden(screen);
        });
    }

    /**
     * Tests that the level selection constructor builds all level and back buttons.
     */
    @Test
    @Timeout(10)
    void levelSelectionScreenBuildsAllLevelButtonsAndBackButton() {
        FxTestSupport.runOnFxThread(() -> {
            LevelSelectionScreen screen = new LevelSelectionScreen(null);

            assertEquals(7, screen.getChildren().size());
            assertEquals(1, countChildrenOfType(screen, ImageView.class));
            assertEquals(6, countChildrenOfType(screen, Button.class));
            assertAllButtonsHidden(screen);
        });
    }

    /**
     * Tests that the help and credit screen constructors build a background and back button.
     */
    @Test
    @Timeout(10)
    void howToPlayAndCreditScreensBuildBackgroundAndBackButton() {
        FxTestSupport.runOnFxThread(() -> {
            HowToPlayScreen howToPlayScreen = new HowToPlayScreen(null);
            CreditScreen creditScreen = new CreditScreen(null);

            assertSimpleBackScreen(howToPlayScreen);
            assertSimpleBackScreen(creditScreen);
        });
    }

    /**
     * Tests that the game screen constructor builds gameplay nodes and initial labels.
     */
    @Test
    @Timeout(10)
    void gameScreenBuildsGameplayNodesAndInitialLabels() {
        FxTestSupport.runOnFxThread(() -> {
            GameScreen screen = new GameScreen(null, 1);
            try {
                assertEquals(12, screen.getChildren().size());
                assertEquals(1, countChildrenOfType(screen, ImageView.class));
                assertEquals(5, countChildrenOfType(screen, Button.class));
                assertEquals(3, countChildrenOfType(screen, Label.class));

                List<String> labels = screen.getChildren().stream()
                        .filter(Label.class::isInstance)
                        .map(Label.class::cast)
                        .map(Label::getText)
                        .toList();
                assertTrue(labels.contains("1 / 5"));
                assertTrue(labels.contains("90"));
                assertTrue(labels.contains("0 / 5"));
                assertAllButtonsHidden(screen);
            } finally {
                stopGameTimer(screen);
            }
        });
    }

    /**
     * Tests that button layout scales from the base 1920x1080 artwork coordinates.
     */
    @Test
    @Timeout(10)
    void buttonLayoutScalesFromBaseResolution() {
        FxTestSupport.runOnFxThread(() -> {
            MainMenuScreen screen = new MainMenuScreen(null);
            Button button = new Button();

            screen.setButtonLayout(button, 960, 540, 192, 108);
            screen.resize(1920, 1080);

            assertEquals(960, button.getTranslateX(), 0.01);
            assertEquals(540, button.getTranslateY(), 0.01);
            assertEquals(192, button.getPrefWidth(), 0.01);
            assertEquals(108, button.getPrefHeight(), 0.01);
        });
    }

    /**
     * Checks a simple image screen with one hidden back button.
     *
     * @param screen screen to inspect
     */
    private static void assertSimpleBackScreen(AnchorPane screen) {
        assertEquals(2, screen.getChildren().size());
        assertEquals(1, countChildrenOfType(screen, ImageView.class));
        assertEquals(1, countChildrenOfType(screen, Button.class));
        assertAllButtonsHidden(screen);
    }

    /**
     * Checks that all screen buttons are transparent.
     *
     * @param screen screen to inspect
     */
    private static void assertAllButtonsHidden(AnchorPane screen) {
        screen.getChildren().stream()
                .filter(Button.class::isInstance)
                .map(Button.class::cast)
                .forEach(button -> assertEquals(0, button.getOpacity(), 0.01));
    }

    /**
     * Counts children of a specific JavaFX node type.
     *
     * @param screen screen to inspect
     * @param nodeType child node type to count
     * @return number of matching child nodes
     */
    private static long countChildrenOfType(AnchorPane screen, Class<? extends Node> nodeType) {
        return screen.getChildren().stream()
                .filter(nodeType::isInstance)
                .count();
    }

    /**
     * Stops the GameScreen timer by reflection so the test can finish cleanly.
     *
     * @param screen game screen under test
     */
    private static void stopGameTimer(GameScreen screen) {
        try {
            Method stopTimer = GameScreen.class.getDeclaredMethod("stopTimer");
            stopTimer.setAccessible(true);
            stopTimer.invoke(screen);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Could not stop GameScreen timer", e);
        }
    }
}
