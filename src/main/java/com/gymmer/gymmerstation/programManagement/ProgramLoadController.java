package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramLoadController implements Initializable {
    private ProgramModel programModel = AppConfig.programModel();
    private static int index = -1;

    @FXML
    ListView programList;

    @FXML
    Button btnStart;

    @FXML
    Button btnEdit;

    @FXML
    Button btnDelete;

    @FXML
    Button btnReturn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> observableList = FXCollections.observableList(programModel.showProgramList());
        programList.setItems(observableList);

        btnDelete.setOnAction(event -> handleBtnDelete(event));

        btnReturn.setOnAction(event -> {
                Stage stage = (Stage) btnReturn.getScene().getWindow();
                stage.close();
        });
    }

    public void handleBtnDelete(ActionEvent event) {
        index = programList.getSelectionModel().getSelectedIndex();
        if(index > -1) {
            programModel.deleteProgram(index);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    ObservableList<String> observableList = FXCollections.observableList(programModel.showProgramList());
                    programList.setItems(observableList);
                }
            };
            thread.start();
        }
        else {
            return;
        }
        index = -1;
    }
}
