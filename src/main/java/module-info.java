module com.example.proyecto_controlsuscripciones {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens com.example.proyecto_controlsuscripciones to javafx.fxml;
    exports com.example.proyecto_controlsuscripciones;
    opens com.example.proyecto_controlsuscripciones.modelo to javafx.base;
    exports com.example.proyecto_controlsuscripciones.modelo;
}