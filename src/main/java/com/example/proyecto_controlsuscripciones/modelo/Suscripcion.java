package com.example.proyecto_controlsuscripciones.modelo;

import java.time.LocalDate;

public class Suscripcion {
    private int idSuscripcion;
    private String nombre;
    private String categoria;
    private String plan;
    private double precio;
    private LocalDate fechaInicio;
    private LocalDate fechaRenovacion;
    private String estado;
    private int idUsuario;

    //Constructores:
    //Para Insertar:
    public Suscripcion(String nombre, String categoria, String plan, double precio, LocalDate fechaInicio, LocalDate fechaRenovacion, String estado, int idUsuario) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.plan = plan;
        this.precio = precio;
        this.fechaInicio = fechaInicio;
        this.fechaRenovacion = fechaRenovacion;
        this.estado = estado;
        this.idUsuario = idUsuario;
    }
    //Para Modificar:
    public Suscripcion(int idSuscripcion, String nombre, String categoria, String plan, double precio, LocalDate fechaInicio, LocalDate fechaRenovacion, String estado, int idUsuario) {
        this.idSuscripcion = idSuscripcion;
        this.nombre = nombre;
        this.categoria = categoria;
        this.plan = plan;
        this.precio = precio;
        this.fechaInicio = fechaInicio;
        this.fechaRenovacion = fechaRenovacion;
        this.estado = estado;
        this.idUsuario = idUsuario;
    }
    //Constructor Vacío
    public Suscripcion(){}

    //Setters and Getters:
    public int getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(int idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaRenovacion() {
        return fechaRenovacion;
    }

    public void setFechaRenovacion(LocalDate fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    //Método toString
    @Override
    public String toString() {
        return "Suscripcion{" +
                "idSuscripcion=" + idSuscripcion +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", plan='" + plan + '\'' +
                ", precio=" + precio +
                ", fechaInicio=" + fechaInicio +
                ", fechaRenovacion=" + fechaRenovacion +
                ", estado='" + estado + '\'' +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
