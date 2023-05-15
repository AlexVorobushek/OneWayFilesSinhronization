module com.example.onewayfilessinhronization {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.onewayfilessinhronization to javafx.fxml;
    exports com.example.onewayfilessinhronization;
    exports com.example.onewayfilessinhronization.fileSpases;
    opens com.example.onewayfilessinhronization.fileSpases to javafx.fxml;
    exports com.example.onewayfilessinhronization.exceptions;
    opens com.example.onewayfilessinhronization.exceptions to javafx.fxml;
}