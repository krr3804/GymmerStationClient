package com.gymmer.gymmerstation.util;

import com.gymmer.gymmerstation.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

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

    public static Alert generateErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR MESSAGE");
        alert.setHeaderText("ERROR!");
        alert.setContentText(message);
        return alert;
    }

    public static Alert generateConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("ARE YOU SURE?");
        alert.setContentText(message);
        return alert;
    }
}
