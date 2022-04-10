package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Division;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.programOperation.ProgramInformationController;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.gymmer.gymmerstation.util.Util.loadStage;

public class ProgramLoadController implements Initializable {
    private final ProgramService programService = AppConfig.programService();
    private static int index = -1;

    @FXML
    ListView<String> programList;

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
        temp();
        programList.setItems(FXCollections.observableList(programService.showProgramList()));
        btnStart.setOnAction(event -> handleBtnStart(event));
        btnDelete.setOnAction(event -> handleBtnDelete(event));
        btnEdit.setOnAction(event -> handleBtnEdit(event));
        btnReturn.setOnAction(event -> loadStage("main-view.fxml",btnReturn.getScene()));
    }

    private void temp() {
        Exercise exercise1 = new Exercise("Leg Press",3L,4L,100L,"0100");
        Exercise exercise2 = new Exercise("Front Squat",3L,10L,80L,"0130");
        List<Exercise> tmp = new ArrayList<>();
        tmp.add(exercise1);
        tmp.add(exercise2);
        Division division = new Division(1,tmp);
        List<Division> tmpDivList = new ArrayList<>();
        tmpDivList.add(division);
        Program program = new Program("Leg buster","To increase leg weight limit",7L,tmpDivList);
        programService.addProgram(program);
    }

    private void handleBtnStart(ActionEvent event) {
        index = programList.getSelectionModel().getSelectedIndex();
        if(index > -1) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("program-information-view.fxml"));
                Parent root = (Parent) loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                ProgramInformationController programInformationController = loader.getController();
                programInformationController.initProgramData(index);

                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
        index = -1;
    }

    private void handleBtnDelete(ActionEvent event) {
        index = programList.getSelectionModel().getSelectedIndex();
        if(index > -1) {
            programService.deleteProgram(index);
            programList.setItems(FXCollections.observableList(programService.showProgramList()));
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
