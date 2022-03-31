module com.gymmer.gymmerstation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gymmer.gymmerstation to javafx.fxml;
    exports com.gymmer.gymmerstation;
}