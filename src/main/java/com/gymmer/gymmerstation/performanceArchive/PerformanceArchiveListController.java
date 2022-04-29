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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    private boolean status = false;

    @FXML
    private ListView<String> programListInProgress;

    @FXML
    private ListView<String> programListTerminated;

    @FXML
    private Button btnView, btnDelete, btnReturn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
        programListInProgress.setOnMouseClicked(event -> handleListDoubleClickEvent(event));
        btnView.setOnAction(event -> handleBtnViewAction(event));
        btnDelete.setOnAction(event -> handleBtnDeleteAction(event));
        btnReturn.setOnAction(event -> handleBtnReturnAction(event));
    }

    private void handleListDoubleClickEvent(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            selectedItemIndex  = programListInProgress.getSelectionModel().getSelectedIndex();
            Program program = programOperationService.getProgramByIndex(selectedItemIndex,false);
            programOperationService.terminateProgram(program);
            setListView();
        }
    }

    private void handleBtnViewAction(ActionEvent event) {
        checkSelectedList();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("performance-archive-view.fxml"));
            Parent root = loader.load();
            PerformanceArchiveController performanceArchiveController = loader.getController();
            performanceArchiveController.initData(selectedItemIndex,status);
            Stage stage = (Stage) btnView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            selectedItemIndex = -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkSelectedList() {
        if(programListInProgress.getSelectionModel().getSelectedItem() == null && programListTerminated.getSelectionModel().getSelectedItem() != null) {
            selectedItemIndex = programListTerminated.getSelectionModel().getSelectedIndex();
            status = true;
        }
        if(programListInProgress.getSelectionModel().getSelectedItem() != null && programListTerminated.getSelectionModel().getSelectedItem() == null) {
            selectedItemIndex = programListInProgress.getSelectionModel().getSelectedIndex();
            status = false;
        }
    }

    private void setListView() {
        programListTerminated.setItems(FXCollections.observableList(programOperationService.getPerformanceArchiveList(true)));
        programListInProgress.setItems(FXCollections.observableList(programOperationService.getPerformanceArchiveList(false)));
    }

    private void handleBtnDeleteAction(ActionEvent event) {
        checkSelectedList();
        noIndexSelectedValidation(selectedItemIndex);
        Program program = programOperationService.getProgramByIndex(selectedItemIndex,status);
        Alert alert = generateDeleteDataAlert("Data");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            programOperationService.deleteProgramData(program,status);
            selectedItemIndex = -1;
            setListView();
            generateInformationAlert("Data Deleted!").showAndWait();
        } else {
            alert.close();
        }
    }

    private void handleBtnReturnAction(ActionEvent event) {
        loadStage("main-view.fxml", btnReturn.getScene());
    }
}
