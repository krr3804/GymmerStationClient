package com.gymmer.gymmerstation.performanceArchive;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.programManagement.ProgramService;
import com.gymmer.gymmerstation.programOperation.ProgramOperationService;
import com.gymmer.gymmerstation.util.Util;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

import static com.gymmer.gymmerstation.util.Util.loadStage;

public class PerformanceArchiveListController implements Initializable {
    private final ProgramOperationService programOperationService = AppConfig.programOperationService();
    private int selectedItemIndex = -1;

    @FXML
    private ListView<String> programList;

    @FXML
    private Button btnView, btnDelete, btnReturn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        programList.setItems(FXCollections.observableList(programOperationService.getPerformanceArchiveList()));
        btnView.setOnAction(event -> handleBtnViewAction(event));
        btnDelete.setOnAction(event -> handleBtnDeleteAction(event));
        btnReturn.setOnAction(event -> handleBtnReturnAction(event));
    }

    private void handleBtnViewAction(ActionEvent event) {

    }

    private void handleBtnDeleteAction(ActionEvent event) {
        selectedItemIndex = programList.getSelectionModel().getSelectedIndex();
        try {
            programOperationService.deleteProgramData(selectedItemIndex);
            selectedItemIndex = -1;
            programList.setItems(FXCollections.observableList(programOperationService.getPerformanceArchiveList()));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void handleBtnReturnAction(ActionEvent event) {
        loadStage("main-view.fxml", btnReturn.getScene());
    }
}
