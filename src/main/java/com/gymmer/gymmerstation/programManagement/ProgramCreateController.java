package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.domain.Program;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramController implements Initializable {

    private ProgramModel programModel = AppConfig.programModel();

    @FXML
    private TextField inpName;

    @FXML
    private TextArea inpPurpose;

    @FXML
    private TextField inpLength;

    @FXML
    private ChoiceBox<String> inpDivision;

    @FXML
    Button btnAddExercise;

    @FXML
    Button btnSave;

    @FXML
    Button btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddExercise.setOnAction(event -> loadStage("exercise-form-view.fxml"));
        btnSave.setOnAction(event -> handleBtnSaveAction(event));
        btnExit.setOnAction(event -> handleBtnExit(event));
    }

    public void handleBtnSaveAction(ActionEvent event) {
        String name = inpName.getText();
        String purpose = inpPurpose.getText();
        Long length = Long.parseLong(inpLength.getText());
        Long division = Long.parseLong(inpDivision.getValue());
        programModel.addProgram(new Program(name,purpose,length,division));
    }

    private void handleBtnExit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
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
