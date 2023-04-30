package com.example.onewayfilessinhronization;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

public class HelloApplication {
//    @Override
    public void start(Stage stage) throws IOException {
        String originalDirectory = "D:\\projects\\OneWayFilesSinhronization\\src\\main\\java";
//        String copiedDirectory = "";
        TrackedFiles trackedOriginalFiles = new TrackedFiles(originalDirectory);
//        TrackedFiles trackedCopiedFiles = new TrackedFiles(copiedDirectory);
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) throws IOException {
//        launch();
        String originalDirectory = "D:\\projects\\OneWayFilesSinhronization\\.mvn\\wrapper";
        String hashDirectory = "D:\\projects\\OneWayFilesSinhronization\\test";
        TrackedFiles originalDirectoryFiles = new TrackedFiles(originalDirectory);
        originalDirectoryFiles.pushTo(hashDirectory);
//        TrackedFiles trackedCopiedFiles = new TrackedFiles(copiedDirectory);
    }
}