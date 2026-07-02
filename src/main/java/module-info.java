module com.example.proyecto_controlsuscripciones {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyecto_controlsuscripciones to javafx.fxml;
    exports com.example.proyecto_controlsuscripciones;
}