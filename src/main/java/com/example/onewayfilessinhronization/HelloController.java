package com.example.onewayfilessinhronization;

import com.example.onewayfilessinhronization.exceptions.CanNotPushException;
import com.example.onewayfilessinhronization.fileSnapshots.FileSnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HelloController {
    public TextField donorLink;
    public TextField recipientLink;
    @FXML
    private Label status;

    public void onSyncButtonClick(ActionEvent actionEvent) throws IOException {
        status.setText("please wait...");
        try {
            FileSnapshot donor = InitFileSnapshot.initDonor(donorLink.getText());
            FileSnapshot recipient = InitFileSnapshot.initRecipient(recipientLink.getText());
            Pusher.push(donor, recipient);
        } catch (CanNotPushException err) {
            status.setText(err.getMessage());
            return;
        }

        status.setText("Completed");
    }
}