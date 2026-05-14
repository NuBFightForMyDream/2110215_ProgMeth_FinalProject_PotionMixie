package application;

import gui.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX entry point that controls screen navigation for PotionMixie.
 */
public class Main extends Application {
    // What does this class do ?
        // This class will be the main program of this game

    // -- Part 1 : Showing Welcome Screen --
        // Must have : extend Application , start() method , launch in main method
    private Stage stage;
    /** Base game width used by all screens. */
    public static final int GAME_WIDTH = 1920 ;
    /** Base game height used by all screens. */
    public static final int GAME_HEIGHT = 1080 ;

    /**
     * Creates the JavaFX application entry object.
     */
    public Main() {
    }

    /**
     * Sets up the main stage and opens the main menu screen.
     *
     * @param stage primary JavaFX stage for the game
     */
    @Override
    public void start(Stage stage) {
        // define stage for Main class
        this.stage = stage;

        // set title and show welcome screen
        stage.setTitle("PotionMixie : Mix Your Potion With Your Love 😻");
        stage.setWidth(GAME_WIDTH);
        stage.setHeight(GAME_HEIGHT);

        showMainMenuScreen();

        stage.show();
    }

    // define method for Welcome Screen & Navigating to another scene
    /**
     * Shows the main menu screen.
     */
    public void showMainMenuScreen() { // call showWelcomeScreen class
        Scene scene = new Scene(new MainMenuScreen(this), GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Shows the game screen using the currently selected level.
     */
    public void showGameScreen() { // call showGameScreen class
        int selectedLevel = LevelSelectionScreen.currentLevel;
        if (selectedLevel < 1 || selectedLevel > 5) {
            selectedLevel = 1;
        }
        Scene scene = new Scene(new GameScreen(this , selectedLevel), GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Shows the how-to-play screen.
     */
    public void showHowToPlayScreen() { // call showHowToPlayScreen class
        Scene scene = new Scene(new HowToPlayScreen(this), GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Shows the credit screen.
     */
    public void showCreditScreen() { // call showCreditScreen class
        Scene scene = new Scene(new CreditScreen(this), GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Shows the level selection screen.
     */
    public void showLevelSelectionScreen() { // call levelSelectionScreen class
        Scene scene = new Scene(new LevelSelectionScreen(this), GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command line arguments from the JVM
     */
    public static void main(String[] args) {
        launch(args);
    }
}
