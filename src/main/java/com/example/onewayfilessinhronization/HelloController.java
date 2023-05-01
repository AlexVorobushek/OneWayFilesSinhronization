package com.example.onewayfilessinhronization;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

public class HelloController {
    public TextField originalDirectory;
    public TextField editableDirectory;
    @FXML
    private Label status;

    public void onSyncButtonClick(ActionEvent actionEvent) throws IOException {
        if (!Files.isDirectory(Paths.get(originalDirectory.getText())) | !Files.isDirectory(Paths.get(editableDirectory.getText()))){
            status.setText("enter not correct");
            return;
        }
        status.setText("please wait...");
        TrackedFiles originalDirectoryFiles = new TrackedFiles(originalDirectory.getText());
        originalDirectoryFiles.pushTo(editableDirectory.getText());
        status.setText("Completed");
    }
}