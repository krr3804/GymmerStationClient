package com.gymmer.gymmerstation.performanceArchive;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.domain.OperationDataExercise;
import com.gymmer.gymmerstation.domain.OperationDataProgram;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.programManagement.ProgramService;
import com.gymmer.gymmerstation.programOperation.ProgramOperationService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.gymmer.gymmerstation.util.Util.loadStage;

public class PerformanceArchiveController implements Initializable {
    private final ProgramOperationService programOperationService = AppConfig.programOperationService();
    private Program currentProgram = null;

    @FXML
    private Label programName;

    @FXML
    private Button btnReturn;

    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnReturn.setOnAction(event -> handleBtnReturnAction(event));
    }

    private void setTabPane() {
        for(OperationDataProgram dataProgram : programOperationService.getProgramDataList(currentProgram)) {
            StringBuilder title = new StringBuilder();
            title.append(dataProgram.getWeek()).append("-").append(dataProgram.getDivision());
            tabPane.getTabs().add(addTab(title.toString(),dataProgram));
        }
    }

    private Tab addTab(String title, OperationDataProgram dataProgram) {
        Tab tab = new Tab();
        tab.setText(title);
        tab.setContent(getTableView(dataProgram.getOdExerciseList()));
        return tab;
    }

    private TableView<OperationDataExercise> getTableView(List<OperationDataExercise> list) {
        TableView<OperationDataExercise> tableView = new TableView<>();
        tableView.setItems(FXCollections.observableList(list));

        TableColumn<OperationDataExercise, String> nameColumn = new TableColumn<>("NAME");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<OperationDataExercise, Long> setColumn = new TableColumn<>("SET");
        setColumn.setMinWidth(100);
        setColumn.setCellValueFactory(new PropertyValueFactory<>("currentSet"));

        TableColumn<OperationDataExercise, Long> repColumn = new TableColumn<>("REP");
        repColumn.setMinWidth(100);
        repColumn.setCellValueFactory(new PropertyValueFactory<>("rep"));

        TableColumn<OperationDataExercise, Long> weightColumn = new TableColumn<>("WEIGHT");
        weightColumn.setMinWidth(100);
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        TableColumn<OperationDataExercise, String> restColumn = new TableColumn<>("Rest");
        restColumn.setMinWidth(100);
        restColumn.setCellValueFactory(new PropertyValueFactory<>("restTime"));

        TableColumn<OperationDataExercise, String> consumedTimeColumn = new TableColumn<>("TIME CONSUMED");
        consumedTimeColumn.setMinWidth(100);
        consumedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("timeConsumed"));

        tableView.getColumns().addAll(nameColumn,setColumn,repColumn,weightColumn,restColumn,consumedTimeColumn);
        return tableView;
    }

    private void handleBtnReturnAction(ActionEvent event) {
        loadStage("performance-archive-list-view.fxml", btnReturn.getScene());
    }

    public void initData(int index) {
        currentProgram = programOperationService.getProgramByIndex(index);
        programName.setText(currentProgram.getName());
        setTabPane();
    }
}
