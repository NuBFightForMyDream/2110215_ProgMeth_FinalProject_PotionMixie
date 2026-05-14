package gui;

import application.Main;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Main menu screen with navigation buttons to play, help, and credits.
 */
public class MainMenuScreen extends AnchorPane {
    // TODO 0 : Fields
    // ------------------- Zone of defining constant for each button ------------------------------------
    static final double BASE_WIDTH = 1920.0 , BASE_HEIGHT = 1080.0 ;
    static final int PLAY_X = 794 , PLAY_Y = 388 , PLAY_WIDTH = 338 , PLAY_HEIGHT = 123 ;
    static final int HOW_X = 749 , HOW_Y = 550 , HOW_WIDTH = 423 , HOW_HEIGHT = 123 ;
    static final int CREDIT_X = 847 , CREDIT_Y = 725 , CREDIT_WIDTH = 253 , CREDIT_HEIGHT = 123 ;
    // --------------------------------------------------------------------------------------------------

    // This Screen will be welcome page for user (which will be run in Main class)

    // TODO 1 : Constructor for this class (Handling Buttons for each page)
    /**
     * Builds the main menu screen and wires navigation buttons.
     *
     * @param main main application used for screen changes
     */
    public MainMenuScreen(Main main) {
        // 1.1 : using ImageView to add image to VBox
        Image mainMenuImage = new Image(getClass().getClassLoader().getResourceAsStream("HomeScreenPic.png"));
        ImageView mainMenuImageView = new ImageView(mainMenuImage) ;

        // 1.2 : using bind property to fit image to full screen
        mainMenuImageView.fitWidthProperty().bind(widthProperty());
        mainMenuImageView.fitHeightProperty().bind(heightProperty());

        // 1.3 : PlayButton for handling GamePlay page
            // element , visibility , binding to fit screen , handling method
        Button playButton = new Button("play");
        setButtonLayout(playButton, PLAY_X, PLAY_Y, PLAY_WIDTH, PLAY_HEIGHT);
        playButton.setOpacity(0); playButton.setOnAction(e -> main.showLevelSelectionScreen()); // Note that user must select level first

        // 1.4 : HowToPlayButton for handling HowToPlay page
        Button howToPlayButton = new Button("How to play");
        setButtonLayout(howToPlayButton, HOW_X, HOW_Y, HOW_WIDTH, HOW_HEIGHT);
        howToPlayButton.setOpacity(0); howToPlayButton.setOnAction(e -> main.showHowToPlayScreen());

        // 1.5 : CreditButton for Handling Credit page
        Button creditButton = new Button("credit");
        setButtonLayout(creditButton, CREDIT_X, CREDIT_Y, CREDIT_WIDTH, CREDIT_HEIGHT);
        creditButton.setOpacity(0); creditButton.setOnAction(e -> main.showCreditScreen());

        // TODO 2 : Adding Button to Pane's Children
        this.getChildren().addAll(mainMenuImageView, playButton, howToPlayButton, creditButton);
    }

    // TODO 3 : Method of setting button properties
    /**
     * Scales a transparent button from base artwork coordinates.
     *
     * @param button button to position
     * @param x_pos x coordinate from the base design
     * @param y_pos y coordinate from the base design
     * @param width button width from the base design
     * @param height button height from the base design
     */
    public void setButtonLayout(Button button , int x_pos , int y_pos , int width , int height) {
        // using bind properties to set ratio for fitting screen (no need to set initial value)
        button.translateXProperty().bind(widthProperty().multiply(x_pos / BASE_WIDTH));
        button.translateYProperty().bind(heightProperty().multiply(y_pos / BASE_HEIGHT));
        button.prefWidthProperty().bind(widthProperty().multiply(width / BASE_WIDTH));
        button.prefHeightProperty().bind(heightProperty().multiply(height / BASE_HEIGHT));
    }
}
