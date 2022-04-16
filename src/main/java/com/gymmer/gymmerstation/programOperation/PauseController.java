package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.gymmer.gymmerstation.util.Util.closeStage;
import static com.gymmer.gymmerstation.util.Util.loadStage;

public class PauseController implements Initializable {
    private final ProgramOperationService programOperationService = AppConfig.programOperationService();
    @FXML
    private Button btnResume, btnSaveAndExit, btnExit;
    private String option;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnResume.setOnAction(event -> handleBtnResumeAction(event));
        btnSaveAndExit.setOnAction(event -> handleBtnSaveAndExitAction(event));
        btnExit.setOnAction(event -> handleBtnExitAction(event));
    }

    private void handleBtnResumeAction(ActionEvent event) {
        option = "return";
        closeStage(btnResume);
    }

    private void handleBtnSaveAndExitAction(ActionEvent event) {
        option = "saveAndExit";
        closeStage(btnSaveAndExit);
    }

    private void handleBtnExitAction(ActionEvent event) {
        option = "exit";
        closeStage(btnExit);
    }

    public String returnOption() {
        return option;
    }
}
