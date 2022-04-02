package com.gymmer.gymmerstation.programManagement;

import com.gymmer.gymmerstation.AppConfig;
import com.gymmer.gymmerstation.Main;
import com.gymmer.gymmerstation.domain.Program;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.gymmer.gymmerstation.util.Util.closeStage;
import static com.gymmer.gymmerstation.util.Util.loadStage;

public class ProgramCreateController implements Initializable {

    private ProgramModel programModel = AppConfig.programModel();
    private static boolean isNew = true;
    private static int idx = -1;

    @FXML
    private TextField inpName;

    @FXML
    private TextArea inpPurpose;

    @FXML
    private TextField inpLength;

    @FXML
    private ChoiceBox<String> inpDivision;

    @FXML
    Button btnAddExercise;

    @FXML
    Button btnSave;

    @FXML
    Button btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddExercise.setOnAction(event -> loadStage("exercise-form-view.fxml",btnAddExercise.getScene()));
        btnSave.setOnAction(event -> handleBtnSaveAction(event));
        btnExit.setOnAction(event -> {
            if(isNew) {
                loadStage("main-view.fxml", btnExit.getScene());
            }
            else {
                loadStage("load-program-view.fxml", btnExit.getScene());
                isNew = true;
                idx = -1;
            }
        });
    }

    private void handleBtnSaveAction(ActionEvent event) {
        String name = inpName.getText();
        String purpose = inpPurpose.getText();
        Long length = Long.parseLong(inpLength.getText());
        Long division = Long.parseLong(inpDivision.getValue());
        Program program = new Program(name,purpose,length,division);
        if(isNew) {
            programModel.addProgram(program);
        }
        else {
            programModel.editProgram(idx,program);
        }
    }

    public void initData(int index, Program program) {
        inpName.setText(program.getName());
        inpPurpose.setText(program.getPurpose());
        inpLength.setText(program.getLength().toString());
        inpDivision.setValue(program.getDivision().toString());
        isNew = false;
        idx = index;
    }
}
