package com.covidlk;

import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launch extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new StackPane(new Label("test")),200,200);
        stage.setScene(scene);
        stage.show();
    }
}
