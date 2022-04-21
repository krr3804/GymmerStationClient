package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.OperationDataExercise;
import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.programManagement.ProgramService;
import com.gymmer.gymmerstation.util.Util;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static com.gymmer.gymmerstation.util.Util.loadStage;

public class ProgramInformationController implements Initializable {
    private final ProgramService programService = AppConfig.programService();
    private final ProgramOperationService programOperationService = AppConfig.programOperationService();
    private int index;
    private Long week;
    private Long division;
    private String timeConsumed;
    private String pauseOption;

    @FXML
    private Label programNameInfo;

    @FXML
    private Label purposeInfo;

    @FXML
    private Label lengthInfo;

    @FXML
    private Label divisionInfo;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnStart.setOnAction(event -> handleBtnStartAction(event));
        btnExit.setOnAction(event -> handleBtnExitAction(event));
    }

    private void handleBtnStartAction(ActionEvent event) {
        Program program = programService.getProgramById(index);
        List<OperationDataExercise> odeList = new ArrayList<>();
        Stage currentStage = (Stage) btnStart.getScene().getWindow();
        currentStage.hide();
        Long divisionNumber = 0L + division;
        for(Exercise exercise : program.getExerciseByDivision(divisionNumber)) {
            loadOperationStage(exercise);
            odeList.add(new OperationDataExercise(exercise.getName(),exercise.getSet(),exercise.getRep(),exercise.getWeight(),exercise.getRestTime(), exercise.getDivision(),timeConsumed));
            if(pauseOption.equals("saveAndExit")) {
                break;
            }
            if(pauseOption.equals("exit")) {
                exit();
                return;
            }
            loadRestTimeStage(exercise);
            if(pauseOption.equals("saveAndExit")) {
                break;
            }
            if(pauseOption.equals("exit")) {
                exit();
                return;
            }
            pauseOption = "";
            timeConsumed = "";
        }

        programOperationService.saveProgramData(new OperationDataProgram(program,week,division,odeList));
        currentStage.show();
        Platform.runLater(() -> {
            btnStart.setText("Completed");
            btnStart.setDisable(true);
        });
    }

    private void loadOperationStage(Exercise exercise) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("program-operation-view.fxml"));
            Parent root = loader.load();
            ProgramOperationController programOperationController = loader.getController();
            programOperationController.initData(exercise);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnStart.getScene().getWindow());
            stage.showAndWait();
            pauseOption = programOperationController.returnOption();
            timeConsumed = programOperationController.getTimeConsumed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRestTimeStage(Exercise exercise) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("rest-time-view.fxml"));
            Parent root = loader.load();
            RestTimeController restTimeController = loader.getController();
            restTimeController.initData(exercise.getRestTime().substring(0,2),exercise.getRestTime().substring(3,5));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnStart.getScene().getWindow());
            stage.showAndWait();
            pauseOption = restTimeController.returnPauseOption();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exit() {
        loadStage("main-view.fxml",btnStart.getScene());
    }

    private void handleBtnExitAction(ActionEvent event) {
        loadStage("load-program-view.fxml",btnExit.getScene());
    }

    public void initProgramData(int index) {
        Program program = programService.getProgramById(index);
        programNameInfo.setText(program.getName());
        purposeInfo.setText(program.getPurpose());
        lengthInfo.setText(program.getLength().toString());
        week = programOperationService.getCurrentWeek(program);
        division = programOperationService.getCurrentDivision(program);
        divisionInfo.setText("Week " + week + " - " + division);
        this.index = index;
    }
}
