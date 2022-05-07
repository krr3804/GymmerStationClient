package com.gymmer.gymmerstation;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.programManagement.ProgramService;
import com.gymmer.gymmerstation.util.Util;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.gymmer.gymmerstation.util.Util.handleCloseWindowAction;

public class Main extends javafx.application.Application {
    private static ProgramService programService = AppConfig.programService();

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml files/main-view.fxml"));
            Scene scene = new Scene(root);
            Image ico = new Image("file:src/main/resources/com/gymmer/gymmerstation/images/dumbbell.png");
            primaryStage.getIcons().add(ico);
            primaryStage.setTitle("Gymmer Station");
            handleCloseWindowAction(primaryStage);
            primaryStage.setScene(scene);
            primaryStage.show();
//            temp();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


    }

    public static void main(String[] args) {
        launch();
    }

//    private static void temp() {
//        Exercise exercise1 = new Exercise("Leg Press",3L,4L,100L,"00:02",1L);
//        Exercise exercise2 = new Exercise("Front Squat",3L,10L,80L,"00:03",1L);
//        Exercise exercise3 = new Exercise("Back Squat",3L,10L,80L,"00:04",2L);
//        List<Exercise> tmp = new ArrayList<>();
//        tmp.add(exercise1);
//        tmp.add(exercise2);
//        tmp.add(exercise3);
//        Program program = new Program(null,"Leg buster","To increase leg weight limit",7L,2L,tmp);
//        programService.addProgram(program);
//    }
}