package com.gymmer.gymmerstation;

import com.gymmer.gymmerstation.domain.Exercise;
import com.gymmer.gymmerstation.domain.Program;
import com.gymmer.gymmerstation.programManagement.ProgramService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main extends javafx.application.Application {
    private static ProgramService programService = AppConfig.programService();

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Gymmer Station");
        primaryStage.setScene(scene);
        primaryStage.show();
        temp();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void temp() {
        Exercise exercise1 = new Exercise("Leg Press",3L,4L,100L,"00","02");
        Exercise exercise2 = new Exercise("Front Squat",3L,10L,80L,"00","03");
        List<Exercise> tmp = new ArrayList<>();
        tmp.add(exercise1);
        tmp.add(exercise2);
        Map<Integer,List<Exercise>> exerciseMap = new LinkedHashMap<>();
        exerciseMap.put(1,tmp);
        Program program = new Program("Leg buster","To increase leg weight limit",7L,exerciseMap);
        programService.addProgram(program);
    }
}