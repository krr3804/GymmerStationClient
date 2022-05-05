package com.gymmer.gymmerstation.performanceArchive;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.programOperation.ProgramOperationService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.gymmer.gymmerstation.util.CommonValidation.noIndexSelectedValidation;
import static com.gymmer.gymmerstation.util.Util.*;

public class PerformanceArchiveListController implements Initializable {
    private final ProgramOperationService programOperationService = AppConfig.programOperationService();
    private int selectedItemIndex = -1;
    private boolean status = false;

    @FXML
    private ListView<String> programListInProgress;

    @FXML
    private ListView<String> programListTerminated;

    @FXML
    private Button btnView, btnDelete, btnReturn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
        formListDragDetectedAction(programListInProgress);
        formListDragDroppedAction(programListTerminated);
        btnView.setOnAction(event -> handleButtonEvents(event));
        btnDelete.setOnAction(event -> handleButtonEvents(event));
        btnReturn.setOnAction(event -> handleBtnReturnAction(event));
    }

    private void handleButtonEvents(ActionEvent event) {
        try {
            if(event.getSource().equals(btnView)) {
                handleBtnViewAction(event);
            }
            if (event.getSource().equals(btnDelete)) {
                handleBtnDeleteAction(event);
            }
        } catch (IllegalArgumentException e) {
            generateErrorAlert(e.getMessage()).showAndWait();
        }
    }

    private void formListDragDetectedAction(ListView<String> list) {
        list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> listCell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                    }
                };
                listCell.setOnDragDetected((MouseEvent event) -> {
                    selectedItemIndex = programListInProgress.getSelectionModel().getSelectedIndex();
                    Dragboard db = listCell.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(listCell.getItem());
                    db.setContent(content);
                    if(programListTerminated.getItems().isEmpty()) {
                        programListTerminated.getItems().add("");
                    }
                });

                listCell.setOnDragDone((DragEvent event) -> {
                    if(programListTerminated.getItems().get(0).equals("")) {
                        setListView();
                    }
                    selectedItemIndex = -1;
                });

                return listCell;
            }
        });
    }
    private void formListDragDroppedAction(ListView<String> list) {
        list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> listCell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                    }
                };

                listCell.setOnDragEntered(event -> listCell.setStyle( "-fx-background-color: grey;" ));

                listCell.setOnDragExited(event -> listCell.setStyle(""));

                listCell.setOnDragOver( ( DragEvent event ) ->
                {
                    Dragboard db = event.getDragboard();
                    if ( db.hasString() )
                    {
                        event.acceptTransferModes( TransferMode.MOVE );
                    }
                } );

                listCell.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    if (db.hasString()) {
                        terminateProgram(event);
                        event.setDropCompleted(true);
                    } else {
                        event.setDropCompleted(false);
                    }
                    selectedItemIndex = -1;
                });
                return listCell;
            }
        });
    }

    private void terminateProgram(DragEvent event) {
        Program program = programOperationService.getProgramByIndex(selectedItemIndex,false);
        Optional<ButtonType> result = generateTerminateProgramAlert(program.getName()).showAndWait();
        if(result.get() == ButtonType.YES) {
            programOperationService.terminateProgram(program);
            setListView();
        }
    }

    private void handleBtnViewAction(ActionEvent event) {
        checkSelectedList();
        noIndexSelectedValidation(selectedItemIndex);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("performance-archive-view.fxml"));
            Parent root = loader.load();
            PerformanceArchiveController performanceArchiveController = loader.getController();
            performanceArchiveController.initData(selectedItemIndex,status);
            Stage stage = (Stage) btnView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkSelectedList() {
        if(programListInProgress.getSelectionModel().getSelectedItem() == null && programListTerminated.getSelectionModel().getSelectedItem() != null) {
            selectedItemIndex = programListTerminated.getSelectionModel().getSelectedIndex();
            status = true;
        }
        if(programListInProgress.getSelectionModel().getSelectedItem() != null && programListTerminated.getSelectionModel().getSelectedItem() == null) {
            selectedItemIndex = programListInProgress.getSelectionModel().getSelectedIndex();
            status = false;
        }
    }

    private void setListView() {
        programListTerminated.setItems(FXCollections.observableList(programOperationService.getPerformanceArchiveList(true)));
        programListInProgress.setItems(FXCollections.observableList(programOperationService.getPerformanceArchiveList(false)));
    }

    private void handleBtnDeleteAction(ActionEvent event) {
        checkSelectedList();
        noIndexSelectedValidation(selectedItemIndex);
        Program program = programOperationService.getProgramByIndex(selectedItemIndex,status);
        Alert alert = generateDeleteDataAlert("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            programOperationService.deleteProgramData(program,status);
            setListView();
            generateInformationAlert("Data Deleted!").showAndWait();
        } else {
            alert.close();
        }
    }

    private void handleBtnReturnAction(ActionEvent event) {
        loadStage("main-view.fxml", btnReturn.getScene());
    }
}
