package com.gymmer.gymmerstation.exerciseManagement;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ExerciseController implements Initializable {
    private Program currentProgram = null;
    private Long currentDivision = null;
    private List<Exercise> additionList = new ArrayList<>();
    private List<Exercise> deletionList = new ArrayList<>();

    @FXML
    private ListView<String> exerciseListView;

    @FXML
    private TextField inpName;

    @FXML
    private TextField inpSets;

    @FXML
    private TextField inpReps;

    @FXML
    private TextField inpWeight;

    @FXML
    private Spinner<String> inpMinute;

    @FXML
    private Spinner<String> inpSecond;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTimer();
        btnSave.setOnAction(event -> handleBtnSaveAction(event));
        btnDelete.setOnAction(event -> handleBtnDeleteAction(event));
        btnExit.setOnAction(event -> handleBtnExitAction(event));
    }

    public void initData(Program program, Long division) {
        currentProgram = program;
        currentDivision = division;
        exerciseListView.setItems(showExerciseList());
    }

    private void initTimer() {
        List<String> time = new ArrayList<>();
        for(int i = 0; i < 60; i++) {
            time.add(String.format("%02d",i));
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(time);
        SpinnerValueFactory<String> valueFactoryMinute = new SpinnerValueFactory.ListSpinnerValueFactory<String>(observableList);
        SpinnerValueFactory<String> valueFactorySecond = new SpinnerValueFactory.ListSpinnerValueFactory<String>(observableList);
        valueFactoryMinute.setValue("00");
        valueFactorySecond.setValue("00");
        inpMinute.setValueFactory(valueFactoryMinute);
        inpSecond.setValueFactory(valueFactorySecond);
    }

    private void handleBtnSaveAction(ActionEvent event) {
        Exercise exercise = new Exercise(inpName.getText(),Long.parseLong(inpSets.getText()),
                Long.parseLong(inpReps.getText()), Long.parseLong(inpWeight.getText()),
                inpMinute.getValue(),inpSecond.getValue(), currentDivision);
        currentProgram.getExerciseList().add(exercise);
        exerciseListView.setItems(showExerciseList());
        additionList.add(exercise);
    }

    private ObservableList<String> showExerciseList() {
        return FXCollections.observableList(currentProgram.getExerciseByDivision(currentDivision)
                .stream().map(Exercise::getName).collect(Collectors.toList()));
    }

    private void handleBtnDeleteAction(ActionEvent event) {
        String name = exerciseListView.getSelectionModel().getSelectedItem();
        Exercise exercise = currentProgram.removeExercise(currentDivision,name);
        exerciseListView.setItems(showExerciseList());
        deletionList.add(exercise);
    }

    private void handleBtnExitAction(ActionEvent event) {
        Util.closeStage(btnExit);
    }

    public List<Exercise> getAdditionList() {
        return additionList;
    }

    public List<Exercise> getDeletionList() {
        return deletionList;
    }

}
