package com.gymmer.gymmerstation.util;

import com.gymmer.gymmerstation.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Util {
    public static void loadStage(String fxml, Scene scene) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxml));
            Stage stage =  (Stage) scene.getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeStage(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    public static void stopWatch() {
        Thread thread = new Thread() {

        }

    }
}
