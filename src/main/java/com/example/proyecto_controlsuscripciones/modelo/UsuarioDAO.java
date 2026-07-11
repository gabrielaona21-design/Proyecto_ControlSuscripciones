package com.example.proyecto_controlsuscripciones.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


    public Usuario buscarPorIdentificador(String iden) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? OR correo = ?";
        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, iden);
            ps.setString(2, iden);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setUsuario(rs.getString("usuario"));
                u.setCorreo(rs.getString("correo"));
                u.setPassword(rs.getString("password"));
                u.setRol(rs.getString("rol"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }

    //Para la ventana clientes
    //MOSTRAR

    public List<Usuario> listarUsuarios() {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Usuario u = new Usuario();

                u.setId_usuario(rs.getInt("id_usuario"));
                u.setUsuario(rs.getString("usuario"));
                u.setCorreo(rs.getString("correo"));
                u.setRol(rs.getString("rol"));

                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

    //BUSCAR

    public Usuario buscarPorId(int id) {

        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";

        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario u = new Usuario();

                u.setId_usuario(rs.getInt("id_usuario"));
                u.setUsuario(rs.getString("usuario"));
                u.setCorreo(rs.getString("correo"));
                u.setRol(rs.getString("rol"));

                return u;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    //ELIMINAR


    public boolean eliminarUsuario(int id) {

        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (Connection conn = conexion.conectarMySQL();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return false;
    }
}