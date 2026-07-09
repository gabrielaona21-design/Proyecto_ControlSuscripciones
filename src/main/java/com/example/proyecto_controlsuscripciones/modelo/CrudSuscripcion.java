package com.example.proyecto_controlsuscripciones.modelo;

import java.util.Map;

public interface CrudSuscripcion {
    Map<Integer,Suscripcion> seleccionarTodo();
    Suscripcion buscar(int id);
    void insertar(Suscripcion s);
    void actualizar(Suscripcion s);
    void eliminar(int id);
}
