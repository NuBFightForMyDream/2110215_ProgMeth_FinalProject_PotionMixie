package gui;

import application.Main;
import gameelement.element.BaseElement;
import gameelement.element.DewDropElement;
import gameelement.element.HeartBerryElement;
import gameelement.element.SparkEmberElement;
import gameelement.element.StarDustElement;
import gameelement.potion.BasePotion;
import gameelement.soul.BaseSoul;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import gamelogic.GameLogic;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Gameplay screen that renders elements, potions, souls, timer, and drag actions.
 */
public class GameScreen extends AnchorPane {

    // TODO 0 : Fields
    // 0.1 : Reference to main game & gameLogic (brain of this game)
    private Main main ; // to call mainGame
    private GameLogic gameLogic ; // to call mainLogic
    private int currentLevel ;

    // 0.2 : Thread / Timer for this game
    private volatile boolean running ;
    private Thread timerThread ;
    private boolean resultAlertShown ;

    // 0.3 : GUI Components
    private Label timeLabel ;
    private Label progressLabel ;
    private Label levelLabel ;

    static final double BASE_WIDTH = 1920.0 , BASE_HEIGHT = 1080.0 ;
    static final int BACK_X = 27 , BACK_Y = 25 , BACK_WIDTH = 96 , BACK_HEIGHT = 104 ;
    static final int HEART_X = 247 , HEART_Y = 874 , HEART_WIDTH = 232 , HEART_HEIGHT = 127 ;
    static final int STAR_X = 499 , STAR_Y = 874 , STAR_WIDTH = 232 , STAR_HEIGHT = 127 ;
    static final int SPARK_X = 753 , SPARK_Y = 874 , SPARK_WIDTH = 232 , SPARK_HEIGHT = 127 ;
    static final int DEW_X = 1003 , DEW_Y = 874 , DEW_WIDTH = 232 , DEW_HEIGHT = 127 ;
    static final int TRASH_X = 561 , TRASH_Y = 444 , TRASH_WIDTH = 176 , TRASH_HEIGHT = 151 ;
    static final int MERGE_STATION_X = 110 , MERGE_STATION_Y = 110 ;
    static final int DELIVER_STATION_X = 775 , DELIVER_STATION_Y = 110 ;
    static final int MERGE_SLOT_WIDTH = 176 , MERGE_SLOT_HEIGHT = 151 ;
    static final int POTION_ICON_SIZE = 100 ;
    static final int SOUL_ICON_SIZE = 100 ;
    static final int MERGE_RESULT_START_SLOT = 0 ;
    static final int[] MERGE_SLOT_X_POSITIONS = {0, 216};
    static final int[] DELIVER_SLOT_X_POSITIONS = {0, 225};
    static final int[] STATION_SLOT_Y_POSITIONS = {0, 172, 359, 530};

    // 0.3.2 : merge area
    private AnchorPane mergeBoard ;
    private AnchorPane soulBeltBoard ;
    private StackPane[] mergeStationSlots ;
    private StackPane[] soulBeltSlots ;
    private StackPane trashDropZone ;
    private BasePotion[] mergeStationPotions ;
    private int nextPotionSlot ;

    // TODO 1 : Constructors for GameScreen 
    /**
     * Builds the gameplay screen for a level and starts its timer.
     *
     * @param main main application used for screen changes
     * @param level selected gameplay level
     */
    public GameScreen(Main main , int level) {
        // 1.0 : define main screen & gameLogic
        this.main = main ;
        this.currentLevel = level ;
        this.gameLogic = new GameLogic(level) ;

        // 1.1 : Handling ImageView for GameScreen
        Image gameImage = new Image(getClass().getClassLoader().getResourceAsStream("GameScreenPic.png"));
        ImageView gameImageView = new ImageView(gameImage);

        gameImageView.fitWidthProperty().bind(widthProperty());
        gameImageView.fitHeightProperty().bind(heightProperty());

        // 1.2 : handling BackButton
        Button backButton = new Button("Back");
        this.setButtonLayout(backButton, BACK_X, BACK_Y, BACK_WIDTH, BACK_HEIGHT);
        backButton.setOpacity(0) ;
        backButton.setOnAction(e -> this.backButtonConfirmationHandler(backButton) );

        // 1.3 : Handling Level Label
        levelLabel = new Label() ;
        levelLabelHandling(levelLabel, level);

        // 1.4 : Handling Time Label
        timeLabel = new Label();
        timeLabelLayoutHandling(timeLabel);

        // 1.5 : Handling Order Label
        progressLabel = new Label() ;
        progressLabelLayoutHandling(progressLabel);

        // 1.6 : Handling merging area
        mergeBoard = createStationPane(MERGE_STATION_X, MERGE_STATION_Y);
        mergeStationSlots = createStationSlots(mergeBoard, MERGE_SLOT_X_POSITIONS);
        mergeStationPotions = new BasePotion[mergeStationSlots.length];
        nextPotionSlot = MERGE_RESULT_START_SLOT;

        soulBeltBoard = createStationPane(DELIVER_STATION_X, DELIVER_STATION_Y);
        soulBeltSlots = createStationSlots(soulBeltBoard, DELIVER_SLOT_X_POSITIONS);
        setupSoulDropTargets();
        updateSoulBeltUI();

        trashDropZone = createTrashDropZone();
        setupTrashDropTarget();

        // 1.7 : Handling Elements Button
        Button heartElementButton = new Button("Heart");
        this.setButtonLayout(heartElementButton, HEART_X, HEART_Y, HEART_WIDTH, HEART_HEIGHT);
        heartElementButton.setOpacity(0);
        heartElementButton.setOnAction(e -> handleElementButton(new HeartBerryElement()));

        Button starElementButton = new Button("Star");
        this.setButtonLayout(starElementButton, STAR_X, STAR_Y, STAR_WIDTH, STAR_HEIGHT);
        starElementButton.setOpacity(0);
        starElementButton.setOnAction(e -> handleElementButton(new StarDustElement()));

        Button sparkElementButton = new Button("Spark");
        this.setButtonLayout(sparkElementButton, SPARK_X, SPARK_Y, SPARK_WIDTH, SPARK_HEIGHT);
        sparkElementButton.setOpacity(0);
        sparkElementButton.setOnAction(e -> handleElementButton(new SparkEmberElement()));

        Button dewElementButton = new Button("Dew");
        this.setButtonLayout(dewElementButton, DEW_X, DEW_Y , DEW_WIDTH, DEW_HEIGHT);
        dewElementButton.setOpacity(0);
        dewElementButton.setOnAction(e -> handleElementButton(new DewDropElement()));

        // 1.8 : Add all element to this Pane
        this.getChildren().addAll(gameImageView , backButton , levelLabel , timeLabel , progressLabel , mergeBoard , soulBeltBoard , trashDropZone) ;
        this.getChildren().addAll(heartElementButton , starElementButton , sparkElementButton , dewElementButton);

        // 1.9 : Start Thread
        startTimerThread();
    }

    // TODO : Methods

    /**
     * Starts the background timer loop for the current game screen.
     */
    private void startTimerThread() {
        running = true;
        timerThread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }

                if (!running) {
                    break;
                }

                Platform.runLater(() -> {
                    if (!running || resultAlertShown || gameLogic.isGameOver()) {
                        return;
                    }

                    gameLogic.updateTimer();
                    updateTimeLabel();

                    if (gameLogic.isGameOver() && gameLogic.isTimeUp() && !gameLogic.isLevelComplete()) {
                        showGameOverAlert();
                    }
                });
            }
        }, "GameTimer-Level-" + currentLevel);
        timerThread.setDaemon(true);
        timerThread.start();
    }

    /**
     * Stops the timer thread when leaving or ending the game.
     */
    private void stopTimer() {
        running = false;
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }

    /**
     * Refreshes the time label from game logic.
     */
    private void updateTimeLabel() {
        timeLabel.setText("" + gameLogic.getTimeLeft());
    }

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

    /**
     * Sets the level label text and position.
     *
     * @param levelLabel label that displays level progress
     * @param level current level number
     */
    public void levelLabelHandling(Label levelLabel, int level) {
        String levelMessage = level + " / 5" ;
        levelLabel.setText(levelMessage);

        levelLabel.setFont( Font.font(28) );
        levelLabel.setLayoutX(1200);
        levelLabel.setLayoutY(225);
    }

    /**
     * Sets the time label text, font, and position.
     *
     * @param timeLabel label that displays remaining time
     */
    public void timeLabelLayoutHandling(Label timeLabel) {
        updateTimeLabel();
        timeLabel.setFont( Font.font(28) );
        timeLabel.setLayoutX(1200);
        timeLabel.setLayoutY(75);
    }

    /**
     * Sets the progress label text, font, and position.
     *
     * @param progressLabel label that displays defeated soul progress
     */
    public void progressLabelLayoutHandling(Label progressLabel) {
        updateProgressLabel() ;
        progressLabel.setFont( Font.font(28) );
        progressLabel.setLayoutX(1200);
        progressLabel.setLayoutY(150);
    }

    /**
     * Shows a confirmation dialog before returning to the main menu.
     *
     * @param backButton button that triggered the handler
     */
    public void backButtonConfirmationHandler(Button backButton) {
        // Sent Alert to user if they want to Confirm Exit
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Back to Main Menu");
        alert.setContentText("Are You Sure you want to go back to Main Menu Screen?");

        // Check if
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stopTimer();
            this.main.showMainMenuScreen();
        }
    }

    /**
     * Refreshes the defeated soul progress label.
     */
    public void updateProgressLabel() {
        if (gameLogic.getFirstSoul() == null) {
            progressLabel.setText("NO SOUL");
            return;
        }

        progressLabel.setText( gameLogic.getDefeatedSoulCount() + " / " + gameLogic.getTotalSoulRequired() ) ;
    }

    /**
     * Sends a selected element to the merge system and updates the screen.
     *
     * @param element element selected by the player
     */
    public void handleElementButton(BaseElement element) {
        BasePotion potion = gameLogic.addElementToMerge(element);
        if (potion != null) {
            showPotionInMergeStation(potion);
        } else {
            clearMergeSelectionSlots();
        }
        updateProgressLabel();
        updateSoulBeltUI();
        if (gameLogic.isLevelComplete()) {
            requestLevelCompleteAlert();
        }
    }

    /**
     * Clears merge selection display after the merge state changes.
     */
    public void updateMergeBoardUI() {
        clearMergeSelectionSlots();
    }

    /**
     * Displays a created potion in the merge station.
     *
     * @param potion potion to display
     */
    public void showPotionInMergeStation(BasePotion potion) {
        clearMergeSelectionSlots();
        int potionSlotIndex = getNextPotionSlotIndex();
        mergeStationPotions[potionSlotIndex] = potion;
        mergeStationSlots[potionSlotIndex].getChildren().setAll(createPotionNode(potion, potionSlotIndex));
    }

    /**
     * Refreshes all visible soul slots from the current soul belt.
     */
    public void updateSoulBeltUI() {
        clearStationSlots(soulBeltSlots);

        int index = 0;
        for (BaseSoul soul : gameLogic.getSoulBeltAsList()) {
            if (index >= soulBeltSlots.length) {
                break;
            }
            soulBeltSlots[index].getChildren().setAll(createSoulNode(soul));
            index++;
        }
    }

    private AnchorPane createStationPane(int xPos, int yPos) {
        AnchorPane stationPane = new AnchorPane();
        stationPane.setLayoutX(xPos);
        stationPane.setLayoutY(yPos);
        return stationPane;
    }

    private StackPane[] createStationSlots(AnchorPane stationPane, int[] slotXPositions) {
        StackPane[] slots = new StackPane[slotXPositions.length * STATION_SLOT_Y_POSITIONS.length];
        int index = 0;
        for (int row = 0; row < STATION_SLOT_Y_POSITIONS.length; row++) {
            for (int column = 0; column < slotXPositions.length; column++) {
                StackPane slot = createStationSlot();
                slot.setLayoutX(slotXPositions[column]);
                slot.setLayoutY(STATION_SLOT_Y_POSITIONS[row]);
                stationPane.getChildren().add(slot);
                slots[index] = slot;
                index++;
            }
        }
        return slots;
    }

    private StackPane createStationSlot() {
        StackPane slot = new StackPane();
        slot.setAlignment(Pos.CENTER);
        slot.setPrefSize(MERGE_SLOT_WIDTH, MERGE_SLOT_HEIGHT);
        slot.setMinSize(MERGE_SLOT_WIDTH, MERGE_SLOT_HEIGHT);
        slot.setMaxSize(MERGE_SLOT_WIDTH, MERGE_SLOT_HEIGHT);
        return slot;
    }

    private StackPane createTrashDropZone() {
        StackPane trashZone = new StackPane();
        trashZone.setLayoutX(TRASH_X);
        trashZone.setLayoutY(TRASH_Y);
        trashZone.setPrefSize(TRASH_WIDTH, TRASH_HEIGHT);
        trashZone.setMinSize(TRASH_WIDTH, TRASH_HEIGHT);
        trashZone.setMaxSize(TRASH_WIDTH, TRASH_HEIGHT);
        trashZone.setPickOnBounds(true);
        return trashZone;
    }

    private void clearMergeSelectionSlots() {
        for (int index = 0; index < MERGE_RESULT_START_SLOT; index++) {
            mergeStationSlots[index].getChildren().clear();
        }
    }

    private int getNextPotionSlotIndex() {
        for (int index = MERGE_RESULT_START_SLOT; index < mergeStationPotions.length; index++) {
            if (mergeStationPotions[index] == null) {
                return index;
            }
        }

        if (nextPotionSlot >= mergeStationPotions.length) {
            nextPotionSlot = MERGE_RESULT_START_SLOT;
        }
        int potionSlotIndex = nextPotionSlot;
        nextPotionSlot++;
        return potionSlotIndex;
    }

    private void clearStationSlots(StackPane[] slots) {
        for (StackPane slot : slots) {
            slot.getChildren().clear();
        }
    }

    private Node createPotionNode(BasePotion potion, int potionSlotIndex) {
        ImageView potionIcon = createImageView(potion.getImagePath(), POTION_ICON_SIZE);
        StackPane potionNode = new StackPane(potionIcon);
        potionNode.setAlignment(Pos.CENTER);
        potionNode.setPickOnBounds(true);
        potionNode.setPrefSize(MERGE_SLOT_WIDTH, MERGE_SLOT_HEIGHT);
        potionNode.setOnDragDetected(event -> {
            Dragboard dragboard = potionNode.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(potionSlotIndex));
            dragboard.setContent(content);
            if (potionIcon.getImage() != null) {
                dragboard.setDragView(potionIcon.snapshot(null, null));
            }
            event.consume();
        });
        return potionNode;
    }

    private void setupSoulDropTargets() {
        for (int index = 0; index < soulBeltSlots.length; index++) {
            int soulIndex = index;
            StackPane soulSlot = soulBeltSlots[index];
            soulSlot.setOnDragOver(event -> {
                if (getPotionSlotIndex(event.getDragboard()) >= MERGE_RESULT_START_SLOT &&
                        soulIndex < gameLogic.getSoulBeltAsList().size()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });
            soulSlot.setOnDragDropped(event -> {
                int potionSlotIndex = getPotionSlotIndex(event.getDragboard());
                boolean success = attackSoulWithDraggedPotion(potionSlotIndex, soulIndex);
                event.setDropCompleted(success);
                event.consume();
            });
        }
    }

    private void setupTrashDropTarget() {
        trashDropZone.setOnDragOver(event -> {
            if (hasPotionAtSlot(getPotionSlotIndex(event.getDragboard()))) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        trashDropZone.setOnDragDropped(event -> {
            int potionSlotIndex = getPotionSlotIndex(event.getDragboard());
            boolean success = removePotionFromMergeStation(potionSlotIndex);
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private boolean attackSoulWithDraggedPotion(int potionSlotIndex, int soulIndex) {
        if (potionSlotIndex < MERGE_RESULT_START_SLOT || potionSlotIndex >= mergeStationPotions.length) {
            return false;
        }

        BasePotion potion = mergeStationPotions[potionSlotIndex];
        if (potion == null) {
            return false;
        }

        boolean attacked = gameLogic.attackSoulAt(soulIndex, potion);
        if (!attacked) {
            return false;
        }

        removePotionFromMergeStation(potionSlotIndex);
        updateProgressLabel();
        updateSoulBeltUI();
        if (gameLogic.isLevelComplete()) {
            requestLevelCompleteAlert();
        }
        return true;
    }

    private boolean hasPotionAtSlot(int potionSlotIndex) {
        return potionSlotIndex >= MERGE_RESULT_START_SLOT &&
                potionSlotIndex < mergeStationPotions.length &&
                mergeStationPotions[potionSlotIndex] != null;
    }

    private boolean removePotionFromMergeStation(int potionSlotIndex) {
        if (!hasPotionAtSlot(potionSlotIndex)) {
            return false;
        }

        mergeStationPotions[potionSlotIndex] = null;
        mergeStationSlots[potionSlotIndex].getChildren().clear();
        return true;
    }

    private void requestLevelCompleteAlert() {
        if (resultAlertShown) {
            return;
        }
        resultAlertShown = true;
        stopTimer();
        Platform.runLater(this::showLevelCompleteAlert);
    }

    private void showLevelCompleteAlert() {
        stopTimer();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Level Complete");
        alert.setHeaderText("Level " + currentLevel + " Complete!");

        if (currentLevel < 5) {
            alert.setContentText("Go to Level " + (currentLevel + 1) + "?");
            ButtonType nextLevelButton = new ButtonType("Next Level");
            ButtonType mainMenuButton = new ButtonType("Main Menu");
            alert.getButtonTypes().setAll(nextLevelButton, mainMenuButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == nextLevelButton) {
                LevelSelectionScreen.currentLevel = currentLevel + 1;
                main.showGameScreen();
            } else {
                main.showMainMenuScreen();
            }
        } else {
            alert.setContentText("You cleared all levels!");
            ButtonType mainMenuButton = new ButtonType("Main Menu");
            alert.getButtonTypes().setAll(mainMenuButton);
            alert.showAndWait();
            main.showMainMenuScreen();
        }
    }

    private void showGameOverAlert() {
        if (resultAlertShown) {
            return;
        }
        resultAlertShown = true;
        stopTimer();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Game Over");
        alert.setHeaderText("Game Over");
        alert.setContentText("Sia Jai Duay Na , Try Again Dai Mai Kub");

        ButtonType retryButton = new ButtonType("Okii , Let's Try Again");
        ButtonType mainMenuButton = new ButtonType("Main Menu");
        alert.getButtonTypes().setAll(retryButton, mainMenuButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == retryButton) {
            LevelSelectionScreen.currentLevel = currentLevel;
            main.showGameScreen();
        } else {
            main.showMainMenuScreen();
        }
    }

    private int getPotionSlotIndex(Dragboard dragboard) {
        if (!dragboard.hasString()) {
            return -1;
        }

        try {
            return Integer.parseInt(dragboard.getString());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private Node createSoulNode(BaseSoul soul) {
        StackPane soulNode = new StackPane(createImageView(soul.getImagePath(), SOUL_ICON_SIZE));
        soulNode.setAlignment(Pos.CENTER);
        soulNode.setPickOnBounds(true);
        Label hpLabel = new Label(String.valueOf(soul.getSoulHP()));
        hpLabel.setFont(Font.font(22));
        hpLabel.setStyle("-fx-background-color: rgba(255,255,255,0.95); -fx-background-radius: 14; -fx-padding: 2 10 2 10; -fx-text-fill: #2b2b2b;");
        StackPane.setAlignment(hpLabel, Pos.TOP_CENTER);
        soulNode.getChildren().add(hpLabel);
        return soulNode;
    }

    private ImageView createImageView(String imagePath, int size) {
        ImageView imageView = new ImageView();
        imageView.setImage(loadImage(imagePath));
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        return imageView;
    }

    private Image loadImage(String imagePath) {
        Image image = loadImageFromResource(imagePath);
        if (isValidImage(image)) {
            return image;
        }

        Image sourceImage = loadImageFromSourceResources(imagePath);
        if (isValidImage(sourceImage)) {
            return sourceImage;
        }

        String fallbackPath = getFallbackImagePath(imagePath);
        if (fallbackPath != null) {
            Image fallbackImage = loadImageFromResource(fallbackPath);
            if (!isValidImage(fallbackImage)) {
                fallbackImage = loadImageFromSourceResources(fallbackPath);
            }
            if (isValidImage(fallbackImage)) {
                System.err.println("Use fallback image: " + imagePath + " -> " + fallbackPath);
                return fallbackImage;
            }
        }

        System.err.println("Missing or invalid image resource: " + imagePath);
        return null;
    }

    private Image loadImageFromResource(String imagePath) {
        if (imagePath == null || imagePath.isBlank()) {
            return null;
        }

        var imageUrl = getClass().getClassLoader().getResource(imagePath);
        if (imageUrl == null) {
            return null;
        }
        return new Image(imageUrl.toExternalForm());
    }

    private Image loadImageFromSourceResources(String imagePath) {
        if (imagePath == null || imagePath.isBlank()) {
            return null;
        }

        Path imageFilePath = Path.of("src", "main", "resources", imagePath);
        if (!Files.isRegularFile(imageFilePath)) {
            return null;
        }
        return new Image(imageFilePath.toUri().toString());
    }

    private boolean isValidImage(Image image) {
        return image != null && !image.isError() && image.getWidth() > 0 && image.getHeight() > 0;
    }

    private String getFallbackImagePath(String imagePath) {
        if (imagePath == null) {
            return null;
        }

        return switch (imagePath) {
            case "DreamMistSoul.png" -> "DreamMist.png";
            case "EnergySplashSoul.png" -> "EnergySplash.png";
            case "NovaSparkSoul.png" -> "NovaSpark.png";
            case "PassionPopSoul.png" -> "PassionPop.png";
            case "SoothingLoveSoul.png" -> "SoothingLove.png";
            case "StarLoveCharmSoul.png" -> "StarLoveCharm.png";
            default -> null;
        };
    }

}
