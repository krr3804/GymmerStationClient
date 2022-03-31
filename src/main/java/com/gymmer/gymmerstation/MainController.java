package com.gymmer.gymmerstation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    Button btnCreateProgram;

    @FXML
    Button btnLoadProgram;

    @FXML
    Button btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCreateProgram.setOnAction(event -> loadStage("create-program-view.fxml"));
        //btnLoadProgram.setOnAction(event -> loadStage(""));
        //btnExit.setOnAction(event -> loadStage(""));
    }

    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

