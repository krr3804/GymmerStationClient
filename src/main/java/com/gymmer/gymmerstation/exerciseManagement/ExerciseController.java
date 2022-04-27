package com.gymmer.gymmerstation.exerciseManagement;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.programManagement.validations.DuplicateExerciseValidation;
import com.gymmer.gymmerstation.programManagement.validations.InputValidation;
import com.gymmer.gymmerstation.util.CommonValidation;
import com.gymmer.gymmerstation.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.gymmer.gymmerstation.programManagement.validations.DuplicateExerciseValidation.*;
import static com.gymmer.gymmerstation.programManagement.validations.InputValidation.inputBlankValidation;
import static com.gymmer.gymmerstation.programManagement.validations.InputValidation.inputMismatchValidationRestTime;
import static com.gymmer.gymmerstation.util.CommonValidation.*;
import static com.gymmer.gymmerstation.util.Util.*;

public class ExerciseController implements Initializable {
    private Program currentProgram = null;
    private Long currentDivision = null;
    private List<Exercise> additionList = new ArrayList<>();
    private List<Exercise> deletionList = new ArrayList<>();

    @FXML
    private ListView<String> exerciseListView;

    @FXML
    private TextField Name, Sets, Reps, Weight;

    @FXML
    private Spinner<String> Minute, Second;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTimer();
        Sets.setOnKeyTyped(event -> checkInputEventValidation(event));
        Reps.setOnKeyTyped(event -> checkInputEventValidation(event));
        Weight.setOnKeyTyped(event -> checkInputEventValidation(event));
        btnSave.setOnAction(event -> checkButtonEventValidation(event));
        btnDelete.setOnAction(event -> checkButtonEventValidation(event));
        btnExit.setOnAction(event -> checkButtonEventValidation(event));
    }

    public void initData(Program program, Long division) {
        currentProgram = program;
        currentDivision = division;
        exerciseListView.setItems(showExerciseList());
    }

    private void checkInputEventValidation(KeyEvent event) {
        TextField field = (TextField) event.getSource();
        try {
            String fieldName = field.getId();
            InputValidation.inputMismatchValidationNumber(field.getText(),fieldName);
        } catch (IllegalArgumentException e) {
            field.clear();
            generateErrorAlert(e.getMessage()).showAndWait();
        }
    }

    private void checkButtonEventValidation(ActionEvent event) {
        try {
            if (event.getSource().equals(btnSave)) {
                handleBtnSaveAction(event);
                generateInformationAlert("Exercise Saved!").showAndWait();
            }
            if (event.getSource().equals(btnDelete)) {
                handleBtnDeleteAction(event);
                generateInformationAlert("Exercise Deleted!").showAndWait();
            }
            if (event.getSource().equals(btnExit)) {
                handleBtnExitAction(event);
            }
        } catch (IllegalArgumentException e) {
            if(e.getMessage().equals("No Item Selected!") || e.getMessage().contains("Is Blank!") ||
                    e.getMessage().equals("Rest Time Is 00:00!") || e.getMessage().equals("Exercise Already In The List!")) {
                generateErrorAlert(e.getMessage()).showAndWait();
            }
        }
    }

    private void initTimer() {
        List<String> minute = new ArrayList<>(60);
        List<String> second = new ArrayList<>(11);
        for(int i = 0; i < 60; i++) {
            if(i % 5 == 0) {
                second.add(String.format("%02d",i));
            }
            minute.add(String.format("%02d",i));
        }
        SpinnerValueFactory<String> valueFactoryMinute = new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(minute));
        SpinnerValueFactory<String> valueFactorySecond = new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(second));
        Minute.setValueFactory(valueFactoryMinute);
        Second.setValueFactory(valueFactorySecond);
    }

    private void handleBtnSaveAction(ActionEvent event) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("Name",Name.getText());
        map.put("Sets", Sets.getText());
        map.put("Reps", Reps.getText());
        map.put("Weight", Weight.getText());
        inputBlankValidation(map);
        String restTime = Minute.getValue() + ":" + Second.getValue();
        inputMismatchValidationRestTime(restTime);
        duplicateExerciseValidation(currentProgram.getExerciseByDivision(currentDivision),Name.getText());
        Exercise exercise = new Exercise(Name.getText(),Long.parseLong(Sets.getText()),
                Long.parseLong(Reps.getText()), Long.parseLong(Weight.getText()),
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
        noItemSelectedValidation(exerciseListView.getSelectionModel().getSelectedItem());
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
