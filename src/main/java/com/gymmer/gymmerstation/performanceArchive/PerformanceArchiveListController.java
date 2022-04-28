package com.gymmer.gymmerstation.performanceArchive;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.programManagement.ProgramService;
import com.gymmer.gymmerstation.programOperation.ProgramOperationService;
import com.gymmer.gymmerstation.util.Util;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.gymmer.gymmerstation.util.CommonValidation.noIndexSelectedValidation;
import static com.gymmer.gymmerstation.util.Util.*;
import static javafx.collections.FXCollections.observableList;

public class PerformanceArchiveListController implements Initializable {
    private final ProgramOperationService programOperationService = AppConfig.programOperationService();
    private int selectedItemIndex = -1;

    @FXML
    private ListView<String> programListProgress;

    @FXML
    private ListView<String> programListTerminated;

    @FXML
    private Button btnView, btnDelete, btnReturn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        programListProgress.setItems(FXCollections.observableList(programOperationService.getPerformanceArchiveList()));
        btnView.setOnAction(event -> handleBtnViewAction(event));
        btnDelete.setOnAction(event -> handleBtnDeleteAction(event));
        btnReturn.setOnAction(event -> handleBtnReturnAction(event));
    }

    private void handleBtnViewAction(ActionEvent event) {
        selectedItemIndex = programListProgress.getSelectionModel().getSelectedIndex();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("performance-archive-view.fxml"));
            Parent root = loader.load();
            PerformanceArchiveController performanceArchiveController = loader.getController();
            performanceArchiveController.initData(selectedItemIndex);
            Stage stage = (Stage) btnView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            selectedItemIndex = -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleBtnDeleteAction(ActionEvent event) {
        selectedItemIndex = programListProgress.getSelectionModel().getSelectedIndex();
        noIndexSelectedValidation(selectedItemIndex);
        Program program = programOperationService.getProgramByIndex(selectedItemIndex);
        Alert alert = generateDeleteDataAlert(programListProgress.getSelectionModel().getSelectedItem());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            programOperationService.deleteProgramData(program);
            selectedItemIndex = -1;
            programListProgress.setItems(FXCollections.observableList(programOperationService.getPerformanceArchiveList()));
            generateInformationAlert("Data Deleted!").showAndWait();
        } else {
            alert.close();
        }
    }

    private void handleBtnReturnAction(ActionEvent event) {
        loadStage("main-view.fxml", btnReturn.getScene());
    }
}
