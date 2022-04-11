package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

import static com.gymmer.gymmerstation.util.Util.*;
import static com.gymmer.gymmerstation.util.Util.loadStage;

public class ProgramEditController implements Initializable {
    private final ProgramService programService = AppConfig.programService();
    private Map<Integer,List<Exercise>> exerciseMap = new LinkedHashMap<>();
    private Integer selectedDivision;
    private int index;

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

    private void handleBtnSaveAction(ActionEvent event) {
        String name = inpName.getText();
        String purpose = inpPurpose.getText();
        Long length = Long.parseLong(inpLength.getText());
        Program program = new Program(name,purpose,length,exerciseMap);
        programService.editProgram(index,program);

    }

    private void handleBtnExitAction(ActionEvent event) {
        loadStage("load-program-view.fxml", btnExit.getScene());
    }

    private void showDivisionList(ActionEvent event) {
        int divCount = Integer.parseInt(inpDivision.getValue());
        exerciseMap = new LinkedHashMap<>(programService.createExerciseMap(divCount));
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

    public void initEditData(int index) {
        Program program = programService.getProgram(index);
        inpName.setText(program.getName());
        inpPurpose.setText(program.getPurpose());
        inpLength.setText(program.getLength().toString());
        inpDivision.setValue(""+program.getExerciseMap().size());
        exerciseMap = new LinkedHashMap<>(program.getExerciseMap());
        this.index = index;
        divisionListView.setItems(FXCollections.observableList(new ArrayList<>(exerciseMap.keySet())));
    }
}
