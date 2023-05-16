package com.example.onewayfilessinhronization;

import com.example.onewayfilessinhronization.exceptions.CanNotPushException;
import com.example.onewayfilessinhronization.exceptions.NotCorrectLinkException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

public class HelloController {
    public TextField donorLink;
    public TextField recipientLink;
    @FXML
    private Label status;

    public void onSyncButtonClick(ActionEvent actionEvent) throws IOException {
        status.setText("please wait...");
        Pusher pusher;
        try {
            pusher = new Pusher(donorLink.getText(), recipientLink.getText());
        } catch (CanNotPushException err) {
            status.setText(err.getMessage());
            return;
        }

        pusher.push();
        status.setText("Completed");
    }
}