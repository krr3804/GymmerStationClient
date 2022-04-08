package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Division;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.exerciseManagement.ExerciseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.gymmer.gymmerstation.util.Util.loadStage;

public class ProgramEditController implements Initializable {
    private ProgramService programModel = AppConfig.programService();
    private List<Division> divList = new ArrayList<>();
    private int index;
    private static int selectedDivisionIndex;

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
        Program program = new Program(name,purpose,length,divList);
        programModel.editProgram(index,program);

    }

    private void handleBtnExitAction(ActionEvent event) {
        loadStage("load-program-view.fxml", btnExit.getScene());
    }

    private void showDivisionList(ActionEvent event) {
        int divCount = Integer.parseInt(inpDivision.getValue());
        inpDivision.setOnMouseClicked(event1 -> initDivisionList(divCount));
        divisionListView.setItems(FXCollections.observableList(divList.stream().map(Division::getNumber).collect(Collectors.toList())));
    }

    private void initDivisionList(int divCount) {
        if(!divList.isEmpty()) {
            divList.clear();
        }
        for(int i = 1; i <= divCount; i++) {
            List<Exercise> exerciseList = new ArrayList<>();
            Division division = new Division(i,exerciseList);
            divList.add(division);
        }
    }

    private void handleBtnAddExerciseEvent(ActionEvent event) {
        if(divisionListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        selectedDivisionIndex = divisionListView.getSelectionModel().getSelectedIndex();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("exercise-form-view.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            ExerciseController exerciseController = loader.getController();
            exerciseController.initExerciseList(divList.get(selectedDivisionIndex).getExerciseList());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnAddExercise.getScene().getWindow());
            stage.showAndWait();

            setExerciseData(exerciseController.getExerciseList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setExerciseData(List<Exercise> list) {
        divList.get(selectedDivisionIndex).getExerciseList().clear();
        divList.get(selectedDivisionIndex).getExerciseList().addAll(list);
    }

    public void initEditData(int index) {
        Program program = programModel.getProgram(index);
        inpName.setText(program.getName());
        inpPurpose.setText(program.getPurpose());
        inpLength.setText(program.getLength().toString());
        inpDivision.setValue(""+program.getDivision().size());
        divList.addAll(program.getDivision());
        divisionListView.setItems(FXCollections.observableList(divList.stream().map(Division::getNumber).collect(Collectors.toList())));
        this.index = index;
    }
}
