package gui;

import application.Main;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LevelSelectionScreen extends AnchorPane {
    // TODO 0 : Fields
    // ------------------- Zone of defining constant for each button ------------------------------------
    static final double BASE_WIDTH = 1920.0 , BASE_HEIGHT = 1080.0 ;
    static final int LV1_X = 429 , LV1_Y = 232 , LV1_WIDTH = 1103 , LV1_HEIGHT = 123 ;
    static final int LV2_X = 523 , LV2_Y = 375 , LV2_WIDTH = 900 , LV2_HEIGHT = 123 ;
    static final int LV3_X = 546 , LV3_Y = 528 , LV3_WIDTH = 874 , LV3_HEIGHT = 123 ;
    static final int LV4_X = 462 , LV4_Y = 677 , LV4_WIDTH = 1037 , LV4_HEIGHT = 123 ;
    static final int LV5_X = 462 , LV5_Y = 821 , LV5_WIDTH = 1037 , LV5_HEIGHT = 123 ;
    static final int BACK_X = 64 , BACK_Y = 64 , BACK_WIDTH = 203 , BACK_HEIGHT = 98 ;
    public static int currentLevel ;
    // --------------------------------------------------------------------------------------------------

    // This Screen will be welcome page for user (which will be run in Main class)

    // TODO 1 : Constructor for this class (Handling Buttons for each page)
    public LevelSelectionScreen(Main main) {
        // 1.1 : using ImageView to add image to VBox
        Image levelSelectionImage = new Image(getClass().getClassLoader().getResourceAsStream("LevelSelectionScreenPic.png"));
        ImageView levelSelectionImageView = new ImageView(levelSelectionImage) ;

        // 1.2 : using bind property to fit image to full screen
        levelSelectionImageView.fitWidthProperty().bind(widthProperty());
        levelSelectionImageView.fitHeightProperty().bind(heightProperty());

        // 1.3 : Button for each level
        // element , visibility , binding to fit screen , handling method
        Button lv1Btn = new Button("LV 1");
        setButtonLayout(lv1Btn , LV1_X, LV1_Y, LV1_WIDTH, LV1_HEIGHT);
        lv1Btn.setOpacity(0); lv1Btn.setOnAction(e -> { currentLevel = 1 ; main.showGameScreen(); } );

        Button lv2Btn = new Button("LV 2");
        setButtonLayout(lv2Btn , LV2_X, LV2_Y, LV2_WIDTH, LV2_HEIGHT);
        lv2Btn.setOpacity(0); lv2Btn.setOnAction(e -> { currentLevel = 2 ; main.showGameScreen(); });

        Button lv3Btn = new Button("LV 3");
        setButtonLayout(lv3Btn, LV3_X, LV3_Y, LV3_WIDTH, LV3_HEIGHT);
        lv3Btn.setOpacity(0); lv3Btn.setOnAction(e -> { currentLevel = 3 ; main.showGameScreen(); });

        Button lv4Btn = new Button("LV 4");
        setButtonLayout(lv4Btn, LV4_X, LV4_Y, LV4_WIDTH, LV4_HEIGHT);
        lv4Btn.setOpacity(0); lv4Btn.setOnAction(e -> { currentLevel = 4 ; main.showGameScreen(); });

        Button lv5Btn = new Button("LV 5");
        setButtonLayout(lv5Btn, LV5_X, LV5_Y, LV5_WIDTH, LV5_HEIGHT);
        lv5Btn.setOpacity(0); lv5Btn.setOnAction(e -> { currentLevel = 5 ; main.showGameScreen(); });

        Button backBtn = new Button("Back");
        setButtonLayout(backBtn, BACK_X, BACK_Y, BACK_WIDTH, BACK_HEIGHT);
        backBtn.setOpacity(0); backBtn.setOnAction(e -> main.showMainMenuScreen());

        // TODO 2 : Adding Button to Pane's Children
        this.getChildren().addAll(levelSelectionImageView, lv1Btn, lv2Btn, lv3Btn , lv4Btn , lv5Btn , backBtn);
    }

    // TODO 3 : Method of setting button properties
    public void setButtonLayout(Button button , int x_pos , int y_pos , int width , int height) {
        // using bind properties to set ratio for fitting screen (no need to set initial value)
        button.translateXProperty().bind(widthProperty().multiply(x_pos / BASE_WIDTH));
        button.translateYProperty().bind(heightProperty().multiply(y_pos / BASE_HEIGHT));
        button.prefWidthProperty().bind(widthProperty().multiply(width / BASE_WIDTH));
        button.prefHeightProperty().bind(heightProperty().multiply(height / BASE_HEIGHT));
    }
}