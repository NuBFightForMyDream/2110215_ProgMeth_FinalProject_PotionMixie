package gui;

import application.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class CreditScreen extends AnchorPane {

    // TODO 0 : Fields
    // ------------------- Zone of defining constant for each button ------------------------------------
    static final double BASE_WIDTH = 1920.0 , BASE_HEIGHT = 1080.0 ;
    static final int BACK_X = 64 , BACK_Y = 64 , BACK_WIDTH = 203 , BACK_HEIGHT = 98 ;
    // --------------------------------------------------------------------------------------------------

    public CreditScreen(Main main) {
        // create ImageView to display credit scene
        Image creditImage = new Image(getClass().getClassLoader().getResourceAsStream("CreditScreenPic.png")) ;
        ImageView creditImageView = new ImageView(creditImage) ;

        // 1.2 : using bind property to fit image to full screen
        creditImageView.fitWidthProperty().bind(widthProperty());
        creditImageView.fitHeightProperty().bind(heightProperty());

        // 1.3 : Back Button for turning into main menu
        Button backButton = new Button("Back");
        setButtonLayout(backButton , BACK_X , BACK_Y , BACK_WIDTH , BACK_HEIGHT);
        backButton.setOpacity(0); backButton.setOnAction(e -> main.showMainMenuScreen());

        // TODO 2 : Adding Button to Pane's Children
        this.getChildren().addAll(creditImageView , backButton);

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