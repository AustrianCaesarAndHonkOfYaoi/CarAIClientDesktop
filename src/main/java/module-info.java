module com.example.main.caraiclientdesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires lombok;
    requires commons.validator;


    opens com.example.main.caraiclientdesktop to javafx.fxml;
    exports com.example.main.caraiclientdesktop;
    exports com.example.main.caraiclientdesktop.controller;
    exports com.example.main.caraiclientdesktop.data to com.fasterxml.jackson.databind;
    opens com.example.main.caraiclientdesktop.controller to javafx.fxml;
}
