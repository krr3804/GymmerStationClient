package com.gymmer.gymmerstation.programOperation;

import com.gymmer.gymmerstation.util.Util;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import static com.gymmer.gymmerstation.util.Util.closeStage;

public class RestTimeController implements Initializable {
    private boolean stop;
    private long sec;

    @FXML
    private Label minute, second;

    @FXML
    private Button btnPause;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnPause.setOnAction(event -> handleBtnPauseAction(event));
        start();
    }

    private void start() {
        stop = false;
        Thread thread = new Thread(() -> {
            while (!stop && sec >= 0) {
                if(sec == 0) {
                    Platform.runLater(() -> {
                        closeStage(btnPause);
                    });
                }

                long m = sec % 3600 / 60;
                long s = sec % 60;

                Platform.runLater(() -> {
                    minute.setText(String.format("%02d",m));
                    second.setText(String.format("%02d",s));
                });

                try {
                    Thread.sleep(1000);
                    sec--;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

    }

    private void handleBtnPauseAction(ActionEvent event) {
        if(!stop) {
            stop = true;
            btnPause.setText("Resume");
        } else {
            start();
            btnPause.setText("Pause");
        }
    }

    public void initData(String minInfo, String secInfo) {
        minute.setText(minInfo);
        second.setText(secInfo);
        sec = Long.parseLong(minInfo)*60 + Long.parseLong(secInfo);
    }
}
