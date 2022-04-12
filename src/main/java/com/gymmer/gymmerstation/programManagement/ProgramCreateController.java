package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.exerciseManagement.ExerciseController;
import com.gymmer.gymmerstation.util.Util;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.gymmer.gymmerstation.util.Util.loadExerciseWindow;
import static com.gymmer.gymmerstation.util.Util.loadStage;

public class ProgramCreateController implements Initializable {

    private final ProgramService programService = AppConfig.programService();

    private Map<Integer,List<Exercise>> exerciseMap = new LinkedHashMap<>();
    private static Integer selectedDivision;

    @FXML
    private TextField inpName;

    @FXML
    private TextArea inpPurpose;

    @FXML
    private TextField inpLength;

    @FXML
    private ChoiceBox<String> inpDivision;

    @FXML
    private ListView<Integer> divisionListView;

    @FXML
    Button btnAddExercise;

    @FXML
    Button btnSave;

    @FXML
    Button btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inpDivision.setOnAction(event -> showDivisionList(event));
        btnAddExercise.setOnAction(event -> handleBtnAddExerciseEvent(event));
        btnSave.setOnAction(event -> handleBtnSaveAction(event));
        btnExit.setOnAction(event -> handleBtnExitAction(event));
    }

    private void handleBtnExitAction(ActionEvent event) {
        loadStage("main-view.fxml",btnExit.getScene());
    }

    private void handleBtnSaveAction(ActionEvent event) {
        String name = inpName.getText();
        String purpose = inpPurpose.getText();
        Long length = Long.parseLong(inpLength.getText());
        Program program = new Program(name,purpose,length,exerciseMap);
        programService.addProgram(program);
    }

    private void showDivisionList(ActionEvent event) {
        int divCount = Integer.parseInt(inpDivision.getValue());
        exerciseMap = new LinkedHashMap<Integer,List<Exercise>>(programService.createExerciseMap(divCount));
        divisionListView.setItems(FXCollections.observableList(new ArrayList<>(exerciseMap.keySet())));
    }

    public void handleBtnAddExerciseEvent(ActionEvent event) {
        selectedDivision = divisionListView.getSelectionModel().getSelectedItem();
        try {
            exerciseMap.put(selectedDivision, loadExerciseWindow(exerciseMap,selectedDivision,event));
        } catch (Exception e) {
            return;
        }
        selectedDivision = null;
    }
}
