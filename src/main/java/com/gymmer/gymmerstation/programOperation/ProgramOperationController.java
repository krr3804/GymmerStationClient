package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.OperationDataExercise;
import com.gymmer.gymmerstation.util.Util;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramOperationController implements Initializable {
    private String minInfo;
    private String secInfo;
    private long sec;
    private boolean stop;
    private String pauseOption = "resume";

    @FXML
    private Label exerciseInfo, setInfo, repsInfo, weightInfo, minute, second;

    @FXML
    private Button btnStart, btnDone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnStart.setOnAction(event -> handleBtnStartAction(event));
        btnDone.setOnAction(event -> handleBtnDoneAction(event));
    }
    private void start() {
        stop = false;
        Thread thread = new Thread(() -> {
            while (!stop && sec < 3600) {
                long m = sec % 3600 / 60;
                long s = sec % 60;

                Platform.runLater(() -> {
                    minute.setText(String.format("%02d",m));
                    second.setText(String.format("%02d",s));
                });
                try {
                    Thread.sleep(1000);
                    sec++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void handleBtnStartAction(ActionEvent event) {
        if(btnStart.getText().equals("Start")) {
            start();
            btnStart.setText("Pause");
            return;
        }
        handlePauseAction();
    }

    private void handlePauseAction() {
        stop = true;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("pause-view.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) btnStart.getScene().getWindow();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(currentStage);
            PauseController pauseController = loader.getController();
            stage.showAndWait();
            pauseOption = pauseController.returnOption();
            if(!pauseOption.equals("return")) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        start();
    }

    public String returnOption() {
        return pauseOption;
    }

    private void handleBtnDoneAction(ActionEvent event) {
        Util.closeStage(btnDone);
    }

    public void initData(Exercise exercise, Long currentSet) {
        exerciseInfo.setText(exercise.getName());
        setInfo.setText(currentSet.toString());
        repsInfo.setText(exercise.getRep().toString());
        weightInfo.setText(exercise.getWeight().toString());
        minInfo = exercise.getRestTime().substring(0,2);
        secInfo = exercise.getRestTime().substring(3,5);
    }

    public String getTimeConsumed() {
        return minute.getText()+":"+second.getText();
    }
}
