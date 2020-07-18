package com.covidlk.gui;

import com.covidlk.service.DataFetchService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launch extends Application {

    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage stage) throws Exception {
        // hide application totally
        /*stage.initStyle(StageStyle.UTILITY);
        stage.setOpacity(0);
        stage.show();*/

        // basic view
        /*Scene scene = new Scene(new StackPane(new Label("test")),200,200);
        stage.setScene(scene);
        stage.show();*/

        // show widget with taskbar icon but we do not need that icon because this ois widget
        /*Parent root = FXMLLoader.load(getClass().getResource("/com/covidlk/gui/widget/Widget.fxml"));
        Scene scene = new Scene(new StackPane(root));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();*/

        /*DataFetchService service = new DataFetchService();
        service.getDataForCountry();*/

        stage.initStyle(StageStyle.UTILITY);
        stage.setOpacity(0);
        stage.show();

        Parent root = FXMLLoader.load(getClass().getResource("/com/covidlk/gui/widget/Widget.fxml"));
        Scene widgetScene = new Scene(new StackPane(root));

        Stage widgetStage = new Stage();
        widgetStage.initStyle(StageStyle.UNDECORATED);
        widgetStage.initOwner(stage);
        widgetStage.setScene(widgetScene);
        widgetStage.show();

        //make widget open in right top corner
        Rectangle2D displayMatrix = Screen.getPrimary().getVisualBounds();
        widgetStage.setX(displayMatrix.getMaxX() - (25 + widgetScene.getWidth())); // x direction ->
        widgetStage.setY(displayMatrix.getMinY() + 25);
        // make drag and move
        widgetScene.setOnMouseClicked(mouseEvent -> {
            xOffset = widgetStage.getX() - mouseEvent.getScreenX();
            yOffset = widgetStage.getY() + mouseEvent.getScreenY();
        });
        widgetScene.setOnMouseDragged(mouseEvent -> {
            widgetStage.setX(mouseEvent.getSceneX() + xOffset);
            widgetStage.setY(mouseEvent.getSceneY() + yOffset);
            resetCordinates();
        });
    }

    private void resetCordinates() {
        xOffset = 0;
        yOffset = 0;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
