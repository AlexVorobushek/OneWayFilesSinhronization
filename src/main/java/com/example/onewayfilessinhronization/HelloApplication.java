package com.example.onewayfilessinhronization;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

import static javafx.application.Application.launch;

public class HelloApplication extends Application{
//    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("карамба!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
//        String originalDirectory = "D:\\projects\\OneWayFilesSinhronization\\.mvn\\wrapper";
//        String hashDirectory = "D:\\projects\\OneWayFilesSinhronization\\test";
    }
}