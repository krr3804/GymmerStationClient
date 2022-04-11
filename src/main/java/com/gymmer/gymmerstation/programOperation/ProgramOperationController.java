package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Exercise;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramOperationController implements Initializable {
    private String minInfo;
    private String secInfo;
    private long sec;
    private boolean stop;

    @FXML
    private Label divisionInfo, exerciseInfo, setInfo, repsInfo, weightInfo, minute, second;

    @FXML
    private Button btnPause, btnStart, btnDone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnStart.setOnAction(event -> handleBtnStartAction(event));
        btnPause.setOnAction(event -> handleBtnPauseAction(event));
        btnDone.setOnAction(event -> handleBtnDoneAction(event));
    }

    private void handleBtnStartAction(ActionEvent event) {
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

    private void handleBtnPauseAction(ActionEvent event) {
        stop = true;
    }

    private void handleBtnDoneAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("rest-time-view.fxml"));
            Parent root = loader.load();
            RestTimeController restTimeController = loader.getController();
            restTimeController.initData(minInfo, secInfo);
            Stage stage = (Stage) btnDone.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initData(int divisionNumber, Exercise exercise) {
        divisionInfo.setText(""+divisionNumber);
        exerciseInfo.setText(exercise.getName());
        setInfo.setText(exercise.getSet().toString());
        repsInfo.setText(exercise.getRep().toString());
        weightInfo.setText(exercise.getWeight().toString());
        minInfo = exercise.getMinute();
        secInfo = exercise.getSecond();
    }
}
