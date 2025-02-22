package com.example.stick_hero_final;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("starting_scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        startingscene_controller controller = fxmlLoader.getController();
        scene.setOnMouseClicked(controller::switchToPlayScreen);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
