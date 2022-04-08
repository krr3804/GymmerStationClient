module com.gymmer.gymmerstation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gymmer.gymmerstation to javafx.fxml;
    exports com.gymmer.gymmerstation;
    opens com.gymmer.gymmerstation.programManagement to javafx.fxml;
    exports com.gymmer.gymmerstation.programManagement;
    opens com.gymmer.gymmerstation.exerciseManagement to javafx.fxml;
    exports com.gymmer.gymmerstation.exerciseManagement;
}