package com.gymmer.gymmerstation.programOperation;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramOperationController implements Initializable {
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
        while (!stop) {
            try {
                Thread.sleep(1000);
                sec++;

            } catch (Exception e) {
                e.printStackTrace();
            }

            Long m = sec % 3600 / 60;
            Long s = sec % 60;

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    minute.setText(String.format("%02d",m));
                    second.setText(String.format("%02d",s));
                }
            });
        }
    }

    private void handleBtnPauseAction(ActionEvent event) {
        stop = true;
    }

}
