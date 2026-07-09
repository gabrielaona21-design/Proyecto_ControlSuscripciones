package com.example.proyecto_controlsuscripciones.modelo;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Map;

public class Servicio {
    private CrudSuscripcion implementacion = new ImplCrud();
    public Map<Integer, Suscripcion> seleccionarTodo() {
        return implementacion.seleccionarTodo();
    }
    public Suscripcion buscar(int id) {
        return implementacion.buscar(id);
    }
    public void insertar(Suscripcion suscripcion) {
        implementacion.insertar(suscripcion);
    }
    public void actualizar(Suscripcion suscripcion) {
        implementacion.actualizar(suscripcion);
    }
    public void eliminar(int id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ADVERTENCIA");
        alert.setHeaderText("¿Está seguro que desea eliminar la suscripción?");
        alert.setContentText("Esta acción no se puede deshacer.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                implementacion.eliminar(id);
            }
        });
    }
}
