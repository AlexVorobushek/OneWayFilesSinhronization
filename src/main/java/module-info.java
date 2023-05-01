module com.example.onewayfilessinhronization {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.onewayfilessinhronization to javafx.fxml;
    exports com.example.onewayfilessinhronization;
}