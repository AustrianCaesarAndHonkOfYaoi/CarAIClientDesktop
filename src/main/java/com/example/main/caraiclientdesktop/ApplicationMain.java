package com.example.main.caraiclientdesktop;

import com.example.main.caraiclientdesktop.controller.ControllerMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("ControllerMain.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        ControllerMain cM = fxmlLoader.getController();
        cM.setCurrentMainController(cM);
        stage.setTitle("Car AI");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}