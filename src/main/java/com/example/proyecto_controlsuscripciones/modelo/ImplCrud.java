package com.example.proyecto_controlsuscripciones.modelo;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImplCrud implements CrudSuscripcion {
    private Connection conn = null;

    private Connection conectarMySQL(){
        Conexion conexion = new Conexion();
        conn = conexion.conectarMySQL();
        return conn;
    }

    @Override
    public Map<Integer, Suscripcion> seleccionarTodo() {
        Map<Integer, Suscripcion> mapa = new LinkedHashMap<>();

        try {
            Connection conn = this.conectarMySQL();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT);

            while (rs.next()) {
                Suscripcion suscripcion = new Suscripcion(
                        rs.getInt("id_suscripcion"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getString("plan"),
                        rs.getDouble("precio"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_renovacion").toLocalDate(),
                        rs.getString("estado"),
                        rs.getInt("id_usuario")
                );
                mapa.put(suscripcion.getIdSuscripcion(), suscripcion);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mapa;
    }

    @Override
    public Suscripcion buscar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Suscripcion suscripcion = null;

        try {
            conn = this.conectarMySQL();
            stmt = conn.prepareStatement(SELECT_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                suscripcion = new Suscripcion(
                        rs.getInt("id_suscripcion"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getString("plan"),
                        rs.getDouble("precio"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_renovacion").toLocalDate(),
                        rs.getString("estado"),
                        rs.getInt("id_usuario")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return suscripcion;
    }

    @Override
    public void insertar(Suscripcion s) {
        try {
            Connection conn = this.conectarMySQL();
            PreparedStatement pstmt = conn.prepareStatement(INSERT);
            pstmt.setString(1, s.getNombre());
            pstmt.setString(2, s.getCategoria());
            pstmt.setString(3, s.getPlan());
            pstmt.setDouble(4, s.getPrecio());
            pstmt.setDate(5, Date.valueOf(s.getFechaInicio()));
            pstmt.setDate(6, Date.valueOf(s.getFechaRenovacion()));
            pstmt.setString(7, s.getEstado());
            pstmt.setInt(8, s.getIdUsuario());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actualizar(Suscripcion s) {
        try {
            Connection conn = this.conectarMySQL();
            PreparedStatement pstmt = conn.prepareStatement(UPDATE);
            pstmt.setString(1, s.getNombre());
            pstmt.setString(2, s.getCategoria());
            pstmt.setString(3, s.getPlan());
            pstmt.setDouble(4, s.getPrecio());
            pstmt.setDate(5, Date.valueOf(s.getFechaInicio()));
            pstmt.setDate(6, Date.valueOf(s.getFechaRenovacion()));
            pstmt.setString(7, s.getEstado());
            pstmt.setInt(8, s.getIdSuscripcion());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void eliminar(int id) {
        try {
            Connection conn = this.conectarMySQL();
            PreparedStatement pstmt = conn.prepareStatement(DELETE);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

     @Override
     public Map<Integer, Suscripcion> seleccionarPorUsuario(int idUsuario) {
         Map<Integer, Suscripcion> mapa = new LinkedHashMap<>();
         try {
             Connection conn = this.conectarMySQL();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USUARIO);
             stmt.setInt(1, idUsuario);
             ResultSet rs = stmt.executeQuery();

             while (rs.next()) {
                 Suscripcion suscripcion = new Suscripcion(
                         rs.getInt("id_suscripcion"),
                         rs.getString("nombre"),
                         rs.getString("categoria"),
                         rs.getString("plan"),
                         rs.getDouble("precio"),
                         rs.getDate("fecha_inicio").toLocalDate(),
                         rs.getDate("fecha_renovacion").toLocalDate(),
                         rs.getString("estado"),
                         rs.getInt("id_usuario")
                 );
                 mapa.put(suscripcion.getIdSuscripcion(), suscripcion);
             }
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
         return mapa;
     }


    private final String SELECT = "SELECT * FROM suscripciones";
    private final String SELECT_BY_ID = "SELECT * FROM suscripciones WHERE id_suscripcion=?";
    private final String INSERT = "INSERT INTO suscripciones(nombre, categoria, plan, precio, fecha_inicio, fecha_renovacion, estado, id_usuario) VALUES(?,?,?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE suscripciones SET nombre=?, categoria=?, plan=?, precio=?, fecha_inicio=?, fecha_renovacion=?, estado=? WHERE id_suscripcion=?";
    private final String DELETE = "DELETE FROM suscripciones WHERE id_suscripcion=?";
    //Método Unicamente para el Usuario **PENDIENTE**
    private final String SELECT_BY_USUARIO = "SELECT * FROM suscripciones WHERE id_usuario=?";
}
