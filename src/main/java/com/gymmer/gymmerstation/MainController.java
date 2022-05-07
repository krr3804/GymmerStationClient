package com.gymmer.gymmerstation;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.gymmer.gymmerstation.util.Util.loadStage;

public class MainController implements Initializable {
    @FXML
    private Button btnCreateProgram;

    @FXML
    private Button btnLoadProgram;

    @FXML
    private Button btnPerformanceArchive;

    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCreateProgram.setOnAction(event -> loadStage("fxml files/create-program-view.fxml",btnCreateProgram.getScene()));
        btnLoadProgram.setOnAction(event -> loadStage("fxml files/load-program-view.fxml",btnLoadProgram.getScene()));
        btnPerformanceArchive.setOnAction(event -> loadStage("fxml files/performance-archive-list-view.fxml",btnPerformanceArchive.getScene()));
        btnExit.setOnAction(event -> Platform.exit());
    }
}

