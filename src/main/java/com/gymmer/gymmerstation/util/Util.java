package com.gymmer.gymmerstation.util;

import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.programOperation.PauseController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Util {
    public static void loadStage(String fxml, Scene scene) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxml));
            Stage stage = (Stage) scene.getWindow();
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

    public static void handleCloseWindowAction(Stage window) {
        window.setOnCloseRequest(e -> {
            e.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("Are You Sure To Exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            } else {
                alert.close();
            }
        });
    }

    public static String loadPauseWindow(Stage ownerWindow) {
        String pauseOption = "";
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("pause-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(ownerWindow);
            PauseController pauseController = loader.getController();
            stage.showAndWait();
            pauseOption = pauseController.returnOption();
            if (!pauseOption.equals("resume")) {
                ownerWindow.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pauseOption;
    }

    public static Alert generateDeleteDataAlert(String data) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Are You Sure To Delete?");
        alert.setContentText(data);
        return alert;
    }

    public static Alert generateDeleteDivisionAlert(Long selectedDivision) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Division " + selectedDivision + " Is Not Empty!");
        alert.setContentText("Are You Sure To Delete?");
        return alert;
    }

    public static Alert generateInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(message);
        return alert;
    }

    public static Alert generateSaveAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Data Not Saved!");
        alert.setContentText("Would You Like To Save Before Leaving?");
        return alert;
    }

    public static Alert generateExitProgramAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Program Still In Progress!");
        alert.setContentText("Are You Sure To Exit?");
        return alert;
    }
}