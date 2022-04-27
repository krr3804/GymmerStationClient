package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.exerciseManagement.ExerciseController;
import com.gymmer.gymmerstation.programManagement.validations.InputValidation;
import com.gymmer.gymmerstation.util.CommonValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.gymmer.gymmerstation.programManagement.validations.DivisionValidation.*;
import static com.gymmer.gymmerstation.util.CommonValidation.*;
import static com.gymmer.gymmerstation.util.Util.*;

public class ProgramEditController implements Initializable {
    private final ProgramService programService = AppConfig.programService();
    private Program program = null;
    private List<Integer> divisionList = new ArrayList<>();
    private List<Long> removedDivisions = new ArrayList<>();
    private List<Exercise> additionList = new ArrayList<>();
    private List<Exercise> deletionList = new ArrayList<>();

    @FXML
    private TextField inpName;

    @FXML
    private TextArea inpPurpose;

    @FXML
    private TextField inpLength;

    @FXML
    private ListView<Integer> divisionListView;

    @FXML
    Button btnAddDivision;

    @FXML
    Button btnRemoveDivision;

    @FXML
    Button btnSave;

    @FXML
    Button btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        divisionListView.setOnMouseClicked(event -> handleListDoubleClickEvent(event));
        inpLength.setOnKeyTyped(event -> checkInputEventValidation(event));
        btnAddDivision.setOnAction(event -> checkButtonEventValidation(event));
        btnRemoveDivision.setOnAction(event -> checkButtonEventValidation(event));
        btnSave.setOnAction(event -> checkButtonEventValidation(event));
        btnExit.setOnAction(event -> checkButtonEventValidation(event));
    }

    private void checkInputEventValidation(KeyEvent event) {
        try {
            InputValidation.inputMismatchValidationNumber(inpLength.getText(), "Length");
        } catch (IllegalArgumentException e) {
            inpLength.clear();
            generateErrorAlert(e.getMessage()).showAndWait();
        }
    }

    private void checkButtonEventValidation(ActionEvent event) {
        try {
            if (event.getSource().equals(btnSave)) {
                handleBtnSaveAction(event);
                generateInformationAlert("Program Edited!").showAndWait();
            }
            if (event.getSource().equals(btnAddDivision)) {
                handleBtnAddDivisionEvent(event);
            }
            if (event.getSource().equals(btnRemoveDivision)) {
                handleBtnRemoveDivisionEvent(event);
            }
            if (event.getSource().equals(btnExit)) {
                handleBtnExitAction(event);
            }
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("No Item Selected!") || e.getMessage().contains("Is Blank!") ||
                    e.getMessage().contains("No Exercise Found In Division ") || e.getMessage().equals("No Division Found!")) {
                generateErrorAlert(e.getMessage()).showAndWait();
            }
        }
    }

    private void handleBtnSaveAction(ActionEvent event) {
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("Name", inpName.getText());
        map.put("Purpose", inpPurpose.getText());
        map.put("Length", inpLength.getText());
        InputValidation.inputBlankValidation(map);
        noDivisionValidation(divisionList);
        noExerciseValidation(program,divisionList);
        Program programEdited = new Program(program.getId(),inpName.getText(),inpPurpose.getText(),Long.parseLong(inpLength.getText()),program.countDivision(),program.getExerciseList());
        programService.editProgram(program,programEdited,removedDivisions,additionList,deletionList);
    }

    private void handleBtnExitAction(ActionEvent event) {
        loadStage("load-program-view.fxml",btnExit.getScene());
    }

    private void handleBtnAddDivisionEvent(ActionEvent event) {
        noExerciseValidation(program,divisionList);
        divisionList.add(divisionList.size()+1);
        divisionListView.setItems(getDivision());
    }

    private void handleBtnRemoveDivisionEvent(ActionEvent event) {
        noItemSelectedValidation(divisionListView.getSelectionModel().getSelectedItem());
        Long selectedDivision = divisionListView.getSelectionModel().getSelectedItem().longValue();
        if(emptyDivisionValidation(program,selectedDivision)) {
            removeDivision(selectedDivision);
            return;
        }
        Alert alert = generateDeleteDivisionAlert(selectedDivision);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            removeDivision(selectedDivision);
        } else {
            alert.close();
        }
    }

    private void removeDivision(Long selectedDivision) {
        int index = divisionListView.getSelectionModel().getSelectedIndex();
        divisionList.remove(index);
        for (int i = index; i < divisionList.size(); i++) {
            divisionList.set(index, divisionList.get(index) - 1);
        }
        program.removeExerciseInDivision(selectedDivision);
        divisionListView.setItems(getDivision());
    }

    private ObservableList<Integer> getDivision() {
        return FXCollections.observableList(divisionList);
    }

    private void handleListDoubleClickEvent(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            Long selectedDivision  = divisionListView.getSelectionModel().getSelectedItem().longValue();
            try {
                loadExerciseWindow(program, selectedDivision, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initEditData(int index) {
        program = programService.getProgramById(index);
        inpName.setText(program.getName());
        inpPurpose.setText(program.getPurpose());
        inpLength.setText(program.getLength().toString());
        for(int i = 1; i <= program.getDivisionQty(); i++) {
            divisionList.add(i);
        }
        divisionListView.setItems(getDivision());
    }

    private void loadExerciseWindow(Program program, Long division, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("exercise-form-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            ExerciseController exerciseController = loader.getController();
            exerciseController.initData(program,division);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            handleCloseWindowAction(stage);
            stage.showAndWait();

            additionList.addAll(exerciseController.getAdditionList());
            deletionList.addAll(exerciseController.getDeletionList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
