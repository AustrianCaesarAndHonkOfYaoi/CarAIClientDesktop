module com.example.main.caraiclientdesktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.main.caraiclientdesktop to javafx.fxml;
    exports com.example.main.caraiclientdesktop;
    exports com.example.main.caraiclientdesktop.controller;
    opens com.example.main.caraiclientdesktop.controller to javafx.fxml;
}