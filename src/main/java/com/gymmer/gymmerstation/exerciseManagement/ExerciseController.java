package com.gymmer.gymmerstation.exerciseManagement;

import com.gymmer.gymmerstation.domain.Exercise;
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
    private List<Exercise> exerciseList = new ArrayList<>();

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

    public void initExerciseList(List<Exercise> list) {
        exerciseList.addAll(list);
        exerciseListView.setItems(FXCollections.observableList(exerciseList.stream().map(Exercise::getName).collect(Collectors.toList())));
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
        String name = inpName.getText();
        Long set = Long.parseLong(inpSets.getText());
        Long rep = Long.parseLong(inpReps.getText());
        Long weight = Long.parseLong(inpWeight.getText());
        String minute = ""+inpMinute.getValue();
        String second = ""+inpSecond.getValue();
        exerciseList.add(new Exercise(name,set,rep,weight,minute,second));
        exerciseListView.setItems(FXCollections.observableList(exerciseList.stream().map(Exercise::getName).collect(Collectors.toList())));
    }

    private void handleBtnDeleteAction(ActionEvent event) {
        if(!exerciseList.isEmpty()) {
            int idx = exerciseListView.getSelectionModel().getSelectedIndex();
            exerciseList.remove(idx);
        }
        exerciseListView.setItems(FXCollections.observableList(exerciseList.stream().map(Exercise::getName).collect(Collectors.toList())));
    }

    private void handleBtnExitAction(ActionEvent event) {
        Util.closeStage(btnExit);
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }
}
