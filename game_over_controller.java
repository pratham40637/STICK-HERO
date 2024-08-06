
package com.example.stick_hero_final;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class game_over_controller {

    @FXML
    private ImageView revive;

    @FXML
    private ImageView button2;

    @FXML
    private Label score_death;

    @FXML
    public Label high_score;

    @FXML
    public Label label4;

    @FXML
    public void revive_character(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene3.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) revive.getScene().getWindow();

            HelloController controller = loader.getController();
            scene.setOnKeyPressed(controller::handleKeyPress);
            scene.setOnKeyReleased(controller::handlekeyrelease);

            controller.set_cherries(Integer.valueOf(label4.getText()));

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void revive_character_cherries(MouseEvent event) {

        if (Integer.parseInt(label4.getText()) >= 5) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("scene3.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) revive.getScene().getWindow();

                HelloController controller = loader.getController();
                int previousScore = Integer.parseInt(score_death.getText());
                controller.load_prev_score(previousScore);


                int currentLabel4Value = Integer.parseInt(label4.getText());
                label4.setText(String.valueOf(currentLabel4Value - 5));
                controller.set_cherries(Integer.valueOf(label4.getText()));

                scene.setOnKeyPressed(controller::handleKeyPress);
                scene.setOnKeyReleased(controller::handlekeyrelease);

                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void updatescore(int score1) {
        String str1 = String.valueOf(score1);
        score_death.setText(str1);
    }

    public void update_highschool(int score2) {
        int currentHighScore = Integer.parseInt(high_score.getText());
        if (score2 > currentHighScore) {
            high_score.setText(String.valueOf(score2));
        }
    }
    public void setlabel(int score){
        label4.setText(String.valueOf(score));
    }
}
