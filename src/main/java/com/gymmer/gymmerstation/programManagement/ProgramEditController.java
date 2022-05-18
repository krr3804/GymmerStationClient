package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.exerciseManagement.ExerciseController;
import com.gymmer.gymmerstation.programManagement.validations.InputValidation;
import com.gymmer.gymmerstation.programManagement.validations.ProgramEditionValidation;
import com.gymmer.gymmerstation.programOperation.ProgramOperationService;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

import static com.gymmer.gymmerstation.programManagement.validations.DivisionValidation.*;
import static com.gymmer.gymmerstation.programManagement.validations.InputValidation.inputBlankValidation;
import static com.gymmer.gymmerstation.programManagement.validations.ProgramEditionValidation.*;
import static com.gymmer.gymmerstation.util.Alerts.*;
import static com.gymmer.gymmerstation.util.CommonValidation.noItemSelectedValidation;
import static com.gymmer.gymmerstation.util.Util.loadStage;

public class ProgramEditController implements Initializable {
    private final ProgramService programService = AppConfig.programService();
    private final ProgramOperationService programOperationService = AppConfig.programOperationService();
    private Program program = null;
    private List<Integer> divisionList = new ArrayList<>();
    private List<Exercise> oldExerciseList = new ArrayList<>();

    @FXML
    private TextField inpName;

    @FXML
    private TextArea inpPurpose;

    @FXML
    private TextField inpLength;

    @FXML
    private ListView<Integer> divisionListView;

    @FXML
    private Button btnAddDivision, btnRemoveDivision, btnSave, btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        divisionListView.setOnMouseClicked(event -> handleListDoubleClickEvent(event));
        divisionListView.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if(btnRemoveDivision.isFocused()) {
                return;
            }
            if(!newVal) {
                divisionListView.getSelectionModel().clearSelection();
            }
        });
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
            if(e.getMessage().equals("Data Unsaved!")) {
                Alert alert = generateSaveAlert();
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.YES) {
                    btnSave.fire();
                } else if(result.get() == ButtonType.NO) {
                    loadStage("fxml files/main-view.fxml", btnExit.getScene());
                } else {
                    alert.close();
                }
            } else {
                generateErrorAlert(e.getMessage()).showAndWait();
            }
        }
    }

    private void handleBtnSaveAction(ActionEvent event) {
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("Name", inpName.getText());
        map.put("Purpose", inpPurpose.getText());
        map.put("Length", inpLength.getText());
        inputBlankValidation(map);
        noDivisionValidation(divisionList);
        noExerciseValidation(program,divisionList);
        if(!checkChangeInProgramContents(program,inpName.getText(),inpPurpose.getText(),Long.parseLong(inpLength.getText())) && !checkChangeInExerciseList(oldExerciseList,program.getExerciseList())) {
            throw new IllegalArgumentException("No Change Found!");
        }
        checkCurrentProgress(programOperationService.getProgress(program.getId()),Long.parseLong(inpLength.getText()),program.countDivision());
        Program editedProgram = new Program(program.getId(),inpName.getText(),inpPurpose.getText(),Long.parseLong(inpLength.getText()),program.countDivision(),program.getExerciseList());
        if (checkChangeInProgramContents(program,inpName.getText(),inpPurpose.getText(),Long.parseLong(inpLength.getText()))) {
            programService.editProgram(editedProgram);
        }
        if (checkChangeInExerciseList(oldExerciseList,program.getExerciseList())) {
            programService.replaceExercises(editedProgram);
        }
        loadStage("fxml files/load-program-view.fxml",btnSave.getScene());
    }

    private void handleBtnExitAction(ActionEvent event) {
        if(checkChangeInProgramContents(program,inpName.getText(),inpPurpose.getText(),Long.parseLong(inpLength.getText())) || checkChangeInExerciseList(oldExerciseList,program.getExerciseList())) {
            throw new IllegalArgumentException("Data Unsaved!");
        }
        loadStage("fxml files/load-program-view.fxml",btnExit.getScene());
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
            program.removeExerciseInDivision(selectedDivision);
            removeDivision(selectedDivision);
        } else {
            alert.close();
        }
    }

    private void removeDivision(Long selectedDivision) {
        int index = divisionListView.getSelectionModel().getSelectedIndex();
        divisionList.remove(index);
        for (int i = index; i < divisionList.size(); i++) {
            divisionList.set(i, divisionList.get(i) - 1);
        }
        divisionListView.setItems(getDivision());
    }

    private ObservableList<Integer> getDivision() {
        return FXCollections.observableList(divisionList);
    }

    private void handleListDoubleClickEvent(MouseEvent event) {
        try {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                noItemSelectedValidation(divisionListView.getSelectionModel().getSelectedItem());
                Long selectedDivision = divisionListView.getSelectionModel().getSelectedItem().longValue();
                loadExerciseWindow(program, selectedDivision, event);
            }
        } catch (IllegalArgumentException e) {
            generateErrorAlert(e.getMessage()).showAndWait();
        }
    }

    public void initEditData(Program program) {
        this.program = program;
        oldExerciseList.addAll(program.getExerciseList());
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
            loader.setLocation(Main.class.getResource("fxml files/exercise-form-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Image ico = new Image("file:src/main/resources/com/gymmer/gymmerstation/images/dumbbell.png");
            stage.getIcons().add(ico);
            stage.setTitle("Gymmer Station");
            stage.setScene(new Scene(root));
            ExerciseController exerciseController = loader.getController();
            exerciseController.initData(program,division);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            handleCloseWindowAction(stage);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
