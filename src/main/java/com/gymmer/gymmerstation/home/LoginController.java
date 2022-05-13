package com.gymmer.gymmerstation.home;

import com.gymmer.gymmerstation.util.Alerts;
import com.gymmer.gymmerstation.util.Util;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.gymmer.gymmerstation.home.validations.LoginInputValidation.checkLoginInput;
import static com.gymmer.gymmerstation.util.Alerts.generateErrorAlert;
import static com.gymmer.gymmerstation.util.Alerts.generateExitProgramAlert;
import static com.gymmer.gymmerstation.util.Util.closeStage;
import static com.gymmer.gymmerstation.util.Util.loadStage;

public class LoginController implements Initializable {
    @FXML
    private TextField userId, password;

    @FXML
    private Button btnExit, btnLogIn, btnMoveToRegister;

    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private Socket socket;
    private String passwordDB = "";
    private HashMap<String,String> map;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        socket = User.socket;
        btnLogIn.setOnAction(event -> handleBtnLogin());
        btnExit.setOnAction(event -> handleBtnExit());
        btnMoveToRegister.setOnAction(event -> moveToRegister());
    }

    private void handleBtnLogin() {
        try {
            checkUserID();
            doLogin();
            checkPassword();
        } catch (IllegalArgumentException e) {
            generateErrorAlert(e.getMessage()).showAndWait();
        }
    }

    private void doLogin() {
        checkLoginInput(userId.getText(),password.getText());
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            map = new HashMap<>();
            map.put("login",userId.getText());
            oos.writeObject(map);
            oos.flush();

            ois = new ObjectInputStream(socket.getInputStream());
            passwordDB = (String) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkUserID() {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            map = new HashMap<>();
            map.put("checkExistID", userId.getText());
            oos.writeObject(map);
            oos.flush();

            ois = new ObjectInputStream(socket.getInputStream());
            int checkNum = (int) ois.readObject();

            if(checkNum != 1) {
                userId.clear();
                throw new IllegalArgumentException("ID Does Not Exist!");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkPassword() {
        if(!passwordDB.equals(password.getText())) {
            password.clear();
            throw new IllegalArgumentException("Wrong Password! Please Type Again.");
        }
        System.out.println("login success!");
        User.setUser_id(userId.getText());
        closeStage(btnLogIn);
    }

    private void handleBtnExit() {
        Optional<ButtonType> result = generateExitProgramAlert().showAndWait();
        if(result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    private void moveToRegister() {
        loadStage("fxml files/register-view.fxml",btnMoveToRegister.getScene());
    }
}
