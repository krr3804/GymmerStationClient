package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.domain.Exercise;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramOperationController implements Initializable {
    private String restInfo;
    private long sec;
    private boolean stop;

    @FXML
    private Label divisionInfo;

    @FXML
    private Label exerciseInfo;

    @FXML
    private Label setInfo;

    @FXML
    private Label repsInfo;

    @FXML
    private Label weightInfo;

    @FXML
    private Label minute;

    @FXML
    private Label second;

    @FXML
    private Button btnPause;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnDone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnStart.setOnAction(event -> handleBtnStartAction(event));
        btnPause.setOnAction(event -> handleBtnPauseAction(event));
    }

    private void handleBtnStartAction(ActionEvent event) {
        stop = false;
        Thread thread = new Thread() {
            @Override
            public void run() {
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
                        System.out.println("error occur!!");
                    }
                }
            };
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void handleBtnPauseAction(ActionEvent event) {
        stop = true;
    }

    public void initData(int divisionNumber, Exercise exercise) {
        divisionInfo.setText(""+divisionNumber);
        exerciseInfo.setText(exercise.getName());
        setInfo.setText(exercise.getSet().toString());
        repsInfo.setText(exercise.getRep().toString());
        weightInfo.setText(exercise.getWeight().toString());
        restInfo = exercise.getRest();
    }
}
