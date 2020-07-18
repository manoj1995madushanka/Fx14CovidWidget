package com.covidlk.gui.widget;

import com.covidlk.config.ConfigModel;
import com.covidlk.config.ConfigurationService;
import com.covidlk.model.JsonObjectMapper;
import com.covidlk.service.DataFetchService;
import com.sun.javafx.menu.MenuItemBase;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class WidgetController implements Initializable {
    @FXML
    public AnchorPane rootPane;
    @FXML
    public Text textGlobalRecord, textCountryRecord;

    private ScheduledExecutorService executorService;
    private String lkData;
    private String glData;

    private ConfigModel configModel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSchedular();
    }

    private void initializeSchedular() {
        configModel = new ConfigurationService().getConfigurations();
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::loadData,0,configModel.getRefreshIntervalInSeconds(), SECONDS);
        initializeContextMenu();
    }

    private void loadData() {
        DataFetchService dataFetchService = new DataFetchService();
        JsonObjectMapper outModel = new JsonObjectMapper(dataFetchService.getDataForCountry());
        makeString(outModel);
        inflateData();

        // this line execute in separate gui thread
        Platform.runLater(()->{
            inflateData();
        });
    }

    private void makeString(JsonObjectMapper outModel) {
        lkData = String.format("Total_Cases : %4d | Today_Cases : %3d | Total_Deaths : %3d | Today_Deaths : %2d",
                outModel.getLocal_total_cases(),outModel.getLocal_new_cases(),outModel.getLocal_total_deaths(),outModel.getLocal_today_deaths());
        glData = String.format("Total_Cases : %9d | Today_Cases : %6d | Total_Deaths : %6d | Today_Deaths : %4d",
                outModel.getGlobal_total_cases(),outModel.getGlobal_new_cases(),outModel.getGlobal_deaths(),outModel.getGlobal_new_deaths());

    }

    private void inflateData()
    {
        textCountryRecord.setText(lkData);
        textGlobalRecord.setText(glData);

        readjustWindowSize(textCountryRecord.getScene().getWindow());
    }

    // this will readject window size
    private void readjustWindowSize(Window stage)
    {
        stage.sizeToScene();

        Rectangle2D displayMatrix = Screen.getPrimary().getVisualBounds();
        stage.setX(displayMatrix.getMaxX() - (25 + textCountryRecord.getScene().getWidth())); // x direction ->
        stage.setY(displayMatrix.getMinY()+25);
    }

    //initialize menu item for close application
    private void initializeContextMenu()
    {
        MenuItem exitItem = new MenuItem("exit");
        MenuItem refreshItem = new MenuItem("refresh now");

        exitItem.setOnAction(actionEvent -> {
            System.exit(0);
        });

        refreshItem.setOnAction(actionEvent -> {
            executorService.schedule(this::loadData,0, SECONDS);
        });

        final ContextMenu contextMenu = new ContextMenu(refreshItem,exitItem);

        // add event handler for right mouse click for display contest menu
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED,mouseEvent -> {
            if(mouseEvent.isSecondaryButtonDown())
            {
                contextMenu.show(rootPane,mouseEvent.getScreenX(),mouseEvent.getScreenY());
            }
            else{
                if (contextMenu.isShowing())
                {
                    contextMenu.hide();
                }
            }
        });
    }
}
