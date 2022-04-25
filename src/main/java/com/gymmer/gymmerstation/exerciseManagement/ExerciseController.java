package com.gymmer.gymmerstation.exerciseManagement;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.util.CommonValidation;
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

import static com.gymmer.gymmerstation.util.Util.generateErrorAlert;

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
        btnSave.setOnAction(event -> checkValidation(event));
        btnDelete.setOnAction(event -> checkValidation(event));
        btnExit.setOnAction(event -> checkValidation(event));
    }

    public void initData(Program program, Long division) {
        currentProgram = program;
        currentDivision = division;
        exerciseListView.setItems(showExerciseList());
    }

    private void checkValidation(ActionEvent event) {
        try {
            if (event.getSource().equals(btnSave)) {
                handleBtnSaveAction(event);
            }
            if (event.getSource().equals(btnDelete)) {
                handleBtnDeleteAction(event);
            }
            if (event.getSource().equals(btnExit)) {
                handleBtnExitAction(event);
            }
        } catch (IllegalArgumentException e) {
            if(e.getMessage().equals("No Item Selected!")) {
                generateErrorAlert(e.getMessage()).showAndWait();
            }
        }

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
        String restTime = inpMinute.getValue() + ":" + inpSecond.getValue();
        Exercise exercise = new Exercise(inpName.getText(),Long.parseLong(inpSets.getText()),
                Long.parseLong(inpReps.getText()), Long.parseLong(inpWeight.getText()),
                restTime, currentDivision);
        currentProgram.getExerciseList().add(exercise);
        exerciseListView.setItems(showExerciseList());
        additionList.add(exercise);
    }

    private ObservableList<String> showExerciseList() {
        return FXCollections.observableList(currentProgram.getExerciseByDivision(currentDivision)
                .stream().map(Exercise::getName).collect(Collectors.toList()));
    }

    private void handleBtnDeleteAction(ActionEvent event) {
        CommonValidation.noItemSelectedValidation(exerciseListView.getSelectionModel().getSelectedItem());
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
