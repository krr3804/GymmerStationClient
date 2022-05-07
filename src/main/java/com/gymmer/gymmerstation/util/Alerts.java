package com.gymmer.gymmerstation.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

import java.util.Optional;

public class Alerts {

    private static Alert customizeAlert(Alert.AlertType type) {
        Alert alert = new Alert(type);
        try {
            alert.getDialogPane().getStylesheets().add(Class.forName("com.gymmer.gymmerstation.Main").getResource("cssFiles/alert.css").toExternalForm());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return alert;
    }

    public static Alert generateErrorAlert(String message) {
        Alert alert = customizeAlert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR MESSAGE");
        alert.setHeaderText("ERROR!");
        alert.setContentText(message);
        return alert;
    }

    public static Alert generateCompletionMessage(String name) {
        Alert alert = customizeAlert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText("Congratulations!");
        alert.setContentText("You Completed " + name + "!");
        return alert;
    }

    public static Alert generateTerminateProgramAlert(String name) {
        Alert alert = customizeAlert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("You Still Got Way To Go!");
        alert.setContentText("Are You Sure To End " +  name + "?");
        return alert;
    }

    public static Alert generateDeleteDataAlert(String data) {
        Alert alert = customizeAlert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Are You Sure To Delete?");
        alert.setContentText(data);
        return alert;
    }

    public static Alert generateDeleteDivisionAlert(Long selectedDivision) {
        Alert alert = customizeAlert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Division " + selectedDivision + " Is Not Empty!");
        alert.setContentText("Are You Sure To Delete?");
        return alert;
    }

    public static Alert generateInformationAlert(String message) {
        Alert alert = customizeAlert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(message);
        return alert;
    }

    public static Alert generateSaveAlert() {
        Alert alert = customizeAlert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Data Not Saved!");
        alert.setContentText("Would You Like To Save Before Leaving?");
        return alert;
    }

    public static Alert generateExitProgramAlert() {
        Alert alert = customizeAlert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Program Still In Progress!");
        alert.setContentText("Are You Sure To Exit?");
        return alert;
    }

    public static void handleCloseWindowAction(Stage window) {
        window.setOnCloseRequest(e -> {
            e.consume();
            Alert alert = customizeAlert(Alert.AlertType.CONFIRMATION);
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

    static void handleCloseOperationWindowAction(Stage window) {
        window.setOnCloseRequest(e -> {
            e.consume();
            Alert alert = generateExitProgramAlert();
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            } else {
                alert.close();
            }
        });
    }
}
