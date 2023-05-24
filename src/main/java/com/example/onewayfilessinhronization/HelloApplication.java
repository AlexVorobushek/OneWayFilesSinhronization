package com.example.onewayfilessinhronization;

import com.example.onewayfilessinhronization.exceptions.CanNotPushException;
import com.example.onewayfilessinhronization.fileSnapshots.FileSnapshot;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class HelloApplication extends Application{
    @FXML
    public Button fxButton;
    @Override
    public void start(Stage stage) {
        TextField donorLink = new TextField();
        TextField recipientLink = new TextField();
        Button btn = new Button();
        Label status = new Label();

        donorLink.setPromptText("donor link");
        donorLink.setPromptText("recipient link");

        HashMap<String, String> inputData = InputSafer.getLastInput();

        if (inputData.get("donor")!=null) donorLink.setText(inputData.get("donor"));
        if (inputData.get("recipient")!=null) recipientLink.setText(inputData.get("recipient"));
        btn.setText("synchronize");

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                InputSafer.safeInput(donorLink.getText(), recipientLink.getText());
                status.setText("please wait...");
                try {
                    FileSnapshot donor = InitFileSnapshot.initDonor(donorLink.getText());
                    FileSnapshot recipient = InitFileSnapshot.initRecipient(recipientLink.getText());
                    Pusher.push(donor, recipient);
                } catch (Exception err) {
                    status.setText(err.getMessage());
                    return;
                }
                status.setText("Completed");
            }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, donorLink, recipientLink, btn, status);

        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("карамба!");
        stage.setWidth(300);
        stage.setHeight(200);

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}