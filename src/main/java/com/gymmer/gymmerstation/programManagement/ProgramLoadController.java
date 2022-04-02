package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.util.Util;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.gymmer.gymmerstation.util.Util.closeStage;
import static com.gymmer.gymmerstation.util.Util.loadStage;

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
        programList.setItems(FXCollections.observableList(programModel.showProgramList()));

        btnDelete.setOnAction(event -> handleBtnDelete(event));
        btnEdit.setOnAction(event -> handleBtnEdit(event));
        btnReturn.setOnAction(event -> loadStage("main-view.fxml",btnReturn.getScene()));
    }

    public void handleBtnDelete(ActionEvent event) {
        index = programList.getSelectionModel().getSelectedIndex();
        if(index > -1) {
            programModel.deleteProgram(index);
            programList.setItems(FXCollections.observableList(programModel.showProgramList()));
        }
        else {
            return;
        }
        index = -1;
    }

    public void handleBtnEdit(ActionEvent event) {
        index = programList.getSelectionModel().getSelectedIndex();
        Program program = programModel.getProgram(index);
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("create-program-view.fxml"));
            Parent root = (Parent) loader.load();
            ProgramCreateController programCreateController = loader.getController();
            programCreateController.initData(index, program);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
