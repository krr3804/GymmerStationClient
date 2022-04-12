package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.OperationDataExercise;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.programManagement.ProgramService;
import com.gymmer.gymmerstation.util.Util;
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

public class ProgramInformationController implements Initializable {
    private final ProgramService programService = AppConfig.programService();
    private final ProgramOperationService programOperationService = AppConfig.programOperationService();
    private int index;

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
        Program program = programService.getProgram(index);
        List<OperationDataExercise> odeList = new ArrayList<>();
        Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        currentStage.hide();

        for(Map.Entry<Integer, List<Exercise>> entry : program.getExerciseMap().entrySet()) {
            for(Exercise exercise : entry.getValue()) {
                String timeConsumed = loadOperationStage(entry.getKey(),exercise);
                odeList.add(new OperationDataExercise(entry.getKey(),exercise,timeConsumed));
            }
        }
        currentStage.show();
        programOperationService.updateODPList(program,odeList);
    }

    private String loadOperationStage(int divisionNumber, Exercise exercise) {
        String timeConsumed = "";
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("program-operation-view.fxml"));
            Parent root = loader.load();
            ProgramOperationController programOperationController = loader.getController();
            programOperationController.initData(divisionNumber,exercise);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnStart.getScene().getWindow());
            stage.showAndWait();

            timeConsumed += programOperationController.getTimeConsumed();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeConsumed;
    }


    private void handleBtnExitAction(ActionEvent event) {
        Util.loadStage("load-program-view.fxml",btnExit.getScene());
    }

    public void initProgramData(int index) {
        Program program = programService.getProgram(index);
        programNameInfo.setText(program.getName());
        purposeInfo.setText(program.getPurpose());
        lengthInfo.setText(program.getLength().toString());
        divisionInfo.setText(""+program.getExerciseMap().size());
        this.index = index;
    }
}
