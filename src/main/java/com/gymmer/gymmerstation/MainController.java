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
    Button btnCreateProgram;

    @FXML
    Button btnLoadProgram;

    @FXML
    Button btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCreateProgram.setOnAction(event -> loadStage("create-program-view.fxml",btnCreateProgram.getScene()));
        btnLoadProgram.setOnAction(event -> loadStage("load-program-view.fxml",btnLoadProgram.getScene()));
        btnExit.setOnAction(event -> Platform.exit());
    }
}

