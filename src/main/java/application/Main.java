package application;

import gui.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    // What does this class do ?
        // This class will be the main program of this game

    // -- Part 1 : Showing Welcome Screen --
        // Must have : extend Application , start() method , launch in main method
    private Stage stage;
    public static final int GAME_WIDTH = 1920 ;
    public static final int GAME_HEIGHT = 1080 ;

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
    public void showMainMenuScreen() { // call showWelcomeScreen class
        Scene scene = new Scene(new MainMenuScreen(this), GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
    }
    public void showGameScreen() { // call showGameScreen class
        int selectedLevel = LevelSelectionScreen.currentLevel;
        if (selectedLevel < 1 || selectedLevel > 5) {
            selectedLevel = 1;
        }
        Scene scene = new Scene(new GameScreen(this , selectedLevel), GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
    }
    public void showHowToPlayScreen() { // call showHowToPlayScreen class
        Scene scene = new Scene(new HowToPlayScreen(this), GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
    }
    public void showCreditScreen() { // call showCreditScreen class
        Scene scene = new Scene(new CreditScreen(this), GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
    }

    public void showLevelSelectionScreen() { // call levelSelectionScreen class
        Scene scene = new Scene(new LevelSelectionScreen(this), GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
