package com.example.proyecto_controlsuscripciones.modelo;

import java.sql.*;

public class UsuarioDAO {
    private Conexion conexion = new Conexion();

    public boolean registrar(Usuario user) {
        String sql = "INSERT INTO usuarios (usuario, correo, password, rol) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsuario());
            ps.setString(2, user.getCorreo());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRol());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.getMessage());
            return false;
        }
    }


    public Usuario buscarPorNombre(String nombreUsuario) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";
        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setUsuario(rs.getString("usuario"));
                u.setCorreo(rs.getString("correo"));
                u.setPassword(rs.getString("password")); // Hash de la BD
                u.setRol(rs.getString("rol"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }
}