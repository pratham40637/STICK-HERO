package com.example.stick_hero_final;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class startingscene_controller {
    @FXML
    private ImageView start_game_button;

    @FXML
    public void switchToPlayScreen(MouseEvent event) {
        try {
            // Load the FXML file for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene3.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML
            Scene scene = new Scene(root);

            // Get the current stage
            Stage stage = (Stage) start_game_button.getScene().getWindow();

            // Set up key event handling (if needed)
            HelloController controller = loader.getController();
            scene.setOnKeyPressed(controller::handleKeyPress);
            scene.setOnKeyReleased(controller::handlekeyrelease);
            scene.setOnKeyPressed(controller::handleKeyPress);
            scene.setOnKeyReleased(controller::handleKeyRelease1);

            // Set the new scene
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message)
        }
    }



}
