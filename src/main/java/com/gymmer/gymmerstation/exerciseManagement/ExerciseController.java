package com.gymmer.gymmerstation.exerciseManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.programManagement.ProgramService;
import com.gymmer.gymmerstation.programManagement.validations.InputValidation;
import com.gymmer.gymmerstation.util.Alerts;
import com.gymmer.gymmerstation.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.gymmer.gymmerstation.programManagement.validations.DuplicateExerciseValidation.*;
import static com.gymmer.gymmerstation.programManagement.validations.InputValidation.inputBlankValidation;
import static com.gymmer.gymmerstation.programManagement.validations.InputValidation.inputMismatchValidationRestTime;
import static com.gymmer.gymmerstation.util.CommonValidation.*;
import static javafx.collections.FXCollections.observableList;

public class ExerciseController implements Initializable {
    private final ProgramService programService = AppConfig.programService();
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
            Alerts.generateErrorAlert(e.getMessage()).showAndWait();
        }
    }

    private void checkButtonEventValidation(ActionEvent event) {
        try {
            if (event.getSource().equals(btnSave)) {
                handleBtnSaveAction(event);
                Alerts.generateInformationAlert("Exercise Saved!").showAndWait();
            }
            if (event.getSource().equals(btnDelete)) {
                handleBtnDeleteAction(event);
                Alerts.generateInformationAlert("Exercise Deleted!").showAndWait();
            }
            if (event.getSource().equals(btnExit)) {
                handleBtnExitAction(event);
            }
        } catch (IllegalArgumentException e) {
            Alerts.generateErrorAlert(e.getMessage()).showAndWait();
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
        additionList.add(exercise);
        exerciseListView.setItems(showExerciseList());
        clearData();
    }

    private void clearData() {
        Name.setText("");
        Sets.setText("");
        Reps.setText("");
        Weight.setText("");
        Minute.getValueFactory().setValue("00");
        Second.getValueFactory().setValue("00");
    }

    private ObservableList<String> showExerciseList() {
        return FXCollections.observableList(currentProgram.getExerciseByDivision(currentDivision)
                .stream().map(Exercise::getName).collect(Collectors.toList()));
    }

    private void handleBtnDeleteAction(ActionEvent event) {
        noItemSelectedValidation(exerciseListView.getSelectionModel().getSelectedItem());
        String name = exerciseListView.getSelectionModel().getSelectedItem();
        Alert alert = Alerts.generateDeleteDataAlert(name);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Exercise exercise = currentProgram.removeExercise(currentDivision,name);
            deletionList.add(exercise);
            exerciseListView.setItems(showExerciseList());
        } else {
            alert.close();
        }
    }

    private void handleBtnExitAction(ActionEvent event) {
        Util.closeStage(btnExit);
    }

    public List<Exercise> getAdditionList() {
        return additionList;
    }

    public List<Exercise> getDeletionList(){
        return deletionList;
    }

}
