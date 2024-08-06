package com.example.stick_hero_final;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.*;
import java.util.Random;

public class HelloController {
    @FXML
    private Rectangle Stick;

    @FXML
    private ImageView character;

    @FXML
    private Label label4;



    @FXML
    private Rectangle rectangle1;

    @FXML
    private Rectangle rectangle2;

    @FXML
    private Label score;

    @FXML
    private Rectangle red_rectangle;

    @FXML
    private ImageView cherry;


    @FXML
    private ImageView pause_button;
    private boolean spaceBarPressed = false;
    private boolean downArrowPressed = false;
    private boolean hKeyPressed = false;
    private boolean extensionInProgress = false;
    private boolean rotationInProgress = false;
    private boolean translationInProgress = false;
    private Timeline extensionTimeline;
    private int count1 = 1;

    private int count2=0;

    private int count3=0;
    private int count4=0;

    private static int highscore;
    private boolean gameRunning = true;

    @FXML
    public void handleKeyPress(KeyEvent event) {
        handleKeyPressMethod1(event);
        handleKeyPressMethod2(event);
    }

    @FXML
    public void handle_mouse_click(){

    }

    public void initialize() throws IOException {
      //Sound.bg_music.play();

        loadGame();
    }
    private void handleKeyPressMethod1(KeyEvent event) {
        if (event.getCode() == KeyCode.H && !hKeyPressed ) {
            hKeyPressed = true;
            count4=1;
        }
    }

    private void handleKeyPressMethod2(KeyEvent event) {
        if (gameRunning && event.getCode() == KeyCode.SPACE && !spaceBarPressed) {
            if (count1 == 1) {
                spaceBarPressed = true;
                extendStick();
                count1 +=1;
            }
        }
    }


    @FXML
    public void handlekeyrelease(KeyEvent event)  {
        handleKeyRelease1(event);
        handleKeyRelease2(event);

    }
    public void handleKeyRelease1(KeyEvent event)  {
        if (gameRunning && event.getCode() == KeyCode.SPACE && spaceBarPressed) {
            spaceBarPressed = false;
            if (extensionInProgress) {
                stopExtension();
                rotateStick();
                moveCharacterToEnd();
            }
        }
    }




    public void handleKeyRelease2(KeyEvent event) {
        if (event.getCode() == KeyCode.H && hKeyPressed) {
            hKeyPressed = false;
        }
    }

    private void setHighscore(int count1){
        if(highscore<count1){
            highscore=count1;

        }
    }
    private void extendStick() {
        if (!extensionInProgress) {
            extensionTimeline = new Timeline(new KeyFrame(Duration.millis(5), e -> {
                Stick.setY(Stick.getY() - 1);
                Stick.setHeight(Stick.getHeight() + 1);
            }));
            extensionTimeline.setCycleCount(Timeline.INDEFINITE);
            extensionTimeline.play();
            extensionInProgress = true;
        }
    }

    private void stopExtension() {
        if (extensionTimeline != null) {
            extensionTimeline.stop();
            extensionInProgress = false;
        }
    }




    private void rotateStick() {
        if (!rotationInProgress) {
            Node stick = Stick;
            double rotateTo = 90;

            Rotate rotate = new Rotate(0, Stick.getX() + Stick.getWidth() / 2.0, Stick.getY() + Stick.getHeight());
            stick.getTransforms().add(rotate);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.34), new KeyValue(rotate.angleProperty(), rotateTo))
            );

            timeline.setOnFinished(event -> rotationInProgress = false);

            rotationInProgress = true;
            timeline.play();
        }
    }

    private void setrectangle1(){
        rectangle1.setWidth(rectangle2.getWidth());
    }
    Timeline timeline1;
    int x;
    private void moveCharacterToEnd()  {
        double endX = Stick.getLayoutX() + Stick.getHeight();
        double initialX = character.getTranslateX();

        double distanceToMove = endX - initialX-20;
        double speed = 185;
        Duration duration = Duration.seconds(distanceToMove / speed);

        TranslateTransition translateTransition = new TranslateTransition(duration, character);
        translateTransition.setByX(distanceToMove);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        translateTransition.setOnFinished(event -> {
            translationInProgress = false;
            checkCherryCollision();
            double characterXAfterTranslation = rectangle1.getX() + rectangle1.getWidth() + Stick.getHeight();

            double rectangle2LeftX = rectangle2.getLayoutX();
            double rectangle2RightX = rectangle2LeftX + rectangle2.getWidth();

            if (characterXAfterTranslation >= rectangle2LeftX && characterXAfterTranslation <= rectangle2RightX) {
                setrectangle1();
                setRandomWidthForRectangle2();
                count2 += 1;
                if(characterXAfterTranslation>=red_rectangle.getLayoutX() && characterXAfterTranslation<=( red_rectangle.getLayoutX()+ red_rectangle.getWidth())){
                    count2+=1;
                }
                updatescore(count2);
                setHighscore(count2);
                try {
                    saveGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                resetGame();
                alignStickWithRectangle1();
                placeRedRectangleInMiddle();
                setcherryposition();


            } else {
                switchToDeathScene();
            }
        });

        translateTransition.play();
        translationInProgress = true;
    }

    private boolean isCharacterInverted() {
        return character.getScaleY() < 0;
    }



    private void switchToDeathScene() {
        try {
         //Sound.bg_music.stop();
         saveGame();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("death_scene.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) Stick.getScene().getWindow();

            game_over_controller controller = loader.getController();
            controller.updatescore(count2);

            controller.update_highschool(highscore);
            controller.setlabel(Integer.valueOf(label4.getText()));


            scene.setOnMouseClicked(controller::revive_character);

            stage.setScene(scene);

            controller.setlabel(Integer.valueOf(label4.getText()));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void resetGameState() {
        Stick.setY(24);
        Stick.setHeight(41);
        character.setTranslateX(0);

        clearStickTransformations();
    }

    private void resetStick() {
        Stick.setLayoutX(114);
        Stick.setHeight(20);
        clearStickTransformations();
    }

    private void clearStickTransformations() {
        Stick.getTransforms().clear();
    }

    private void resetGame() {
        resetGameState();
        resetStick();
        stopExtension();
        rotationInProgress = false;
        translationInProgress = false;
        extensionInProgress = false;
        gameRunning = true;
        spaceBarPressed = false;
        cherry.setVisible(true);
        count1 = 1;
    }

    private void setRandomWidthForRectangle2() {
        Random random = new Random();
        double minWidth = 10;
        double maxWidth = 180;

        double randomWidth = minWidth + (maxWidth - minWidth) * random.nextDouble();
        rectangle2.setWidth(randomWidth);


        double minPositionX = rectangle1.getX() + rectangle1.getWidth()+20;


        double maxPositionX = Math.min(600 - randomWidth, minPositionX + 250);

        double randomPositionX = minPositionX + (maxPositionX - minPositionX) * random.nextDouble();
        rectangle2.setLayoutX(randomPositionX);
    }



    private void updatescore(int score1){
        String str1= String.valueOf(score1);
        score.setText(str1);

    }
    private void alignStickWithRectangle1() {
        Stick.setLayoutX(rectangle1.getLayoutX()+ rectangle1.getWidth());

    }
    private void scaleCharacter() {
        double scaleFactor = 0.5; // You can adjust the scale factor as needed
        character.setLayoutX(scaleFactor);
        character.setLayoutY(scaleFactor);
    }

    private void flipCharacterAboutLegs() {
        double pivotX = character.getX() + character.getFitWidth() / 2.0;
        double pivotY = character.getY() + character.getFitHeight();

        Rotate rotate = new Rotate(180, pivotX, pivotY);
        character.getTransforms().add(rotate);
    }
    private void placeRedRectangleInMiddle() {
        double rectangle2CenterX = rectangle2.getLayoutX() + rectangle2.getWidth() / 2.0;
        double rectangle2CenterY = rectangle2.getLayoutY() + rectangle2.getHeight() / 2.0;

        double redRectangleWidth = red_rectangle.getWidth();
        double redRectangleHeight = red_rectangle.getHeight();

        double redRectangleX = rectangle2CenterX - redRectangleWidth / 2.0;
        double redRectangleY = rectangle2CenterY - redRectangleHeight / 2.0;
        redRectangleY-=rectangle2.getHeight()/2;
        redRectangleY+=redRectangleHeight/2;

        red_rectangle.setLayoutX(redRectangleX);
        red_rectangle.setLayoutY(redRectangleY);
    }


    private void checkCherryCollision() {
        if (character.getBoundsInParent().intersects(cherry.getBoundsInParent())  && cherry.isVisible()) {
            cherry.setVisible(false);
        }
    }

    private void setcherryposition(){
        Random random = new Random();

        double minWidth = rectangle1.getLayoutX() + rectangle1.getWidth()-5;
        double maxWidth = rectangle2.getLayoutX();


        double randomPositionX = minWidth + (maxWidth - minWidth) * random.nextDouble();


        cherry.setLayoutX(randomPositionX);

    }

    private void loadGame() throws IOException {

        BufferedReader fp = new BufferedReader(new FileReader("SavedGame.txt"));
        highscore = Integer.parseInt(fp.readLine());
        System.out.println("Failed");
        fp.close();
    }


    public void saveGame() throws IOException {
        PrintWriter fp = new PrintWriter(new FileWriter("SavedGame.txt"));
        fp.println(highscore);
        fp.close();
    }
    public void load_prev_score(int dummy1) {
        count2=dummy1;
        score.setText(String.valueOf(count2));

    }

    public void set_cherries(int val ){
        label4.setText(String.valueOf(val));

    }

}