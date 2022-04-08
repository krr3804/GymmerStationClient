package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Program;
import javafx.collections.FXCollections;
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

import static com.gymmer.gymmerstation.util.Util.loadStage;

public class ProgramLoadController implements Initializable {
    private ProgramService programModel = AppConfig.programService();
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

    private void handleBtnDelete(ActionEvent event) {
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

    private void handleBtnEdit(ActionEvent event) {
        index = programList.getSelectionModel().getSelectedIndex();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("edit-program-view.fxml"));
            Parent root = (Parent) loader.load();
            ProgramEditController programEditController = loader.getController();
            programEditController.initEditData(index);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
