package com.example.proyecto_controlsuscripciones.modelo;

public class Usuario {
    private int id_usuario;
    private String usuario;
    private String correo;
    private String password;
    private String rol;

    public Usuario() {}

    public Usuario(String usuario, String correo, String password, String rol) {
        this.usuario = usuario;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    // Getters y Setters
    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}