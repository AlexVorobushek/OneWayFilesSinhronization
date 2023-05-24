module com.example.onewayfilessinhronization {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.onewayfilessinhronization to javafx.fxml;
    exports com.example.onewayfilessinhronization;

    opens com.example.onewayfilessinhronization.fileSnapshots to javafx.fxml;
    exports com.example.onewayfilessinhronization.fileSnapshots;

    exports com.example.onewayfilessinhronization.exceptions;
    opens com.example.onewayfilessinhronization.exceptions to javafx.fxml;


    exports com.example.onewayfilessinhronization.exceptions.VCExceptions.VCNotCorrectLinkExceptions;
    opens com.example.onewayfilessinhronization.exceptions.VCExceptions.VCNotCorrectLinkExceptions to javafx.fxml;
}