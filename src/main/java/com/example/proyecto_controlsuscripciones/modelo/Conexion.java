package com.example.proyecto_controlsuscripciones.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String JDBC_URL=
            "jdbc:mysql://localhost:3306/control_suscripciones";
    private static final String JDBC_USER="root";
    private static final String JDBC_PASSWORD="";

    public Connection conectarMySQL(){
        Connection conn=null;
        try{
            conn= DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
            System.out.println("Conexion a mysql ok");
        }catch(SQLException ex){
            System.out.println("Error al conectarse al abase de datos"+ex.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        Conexion c=new Conexion();
        c.conectarMySQL();
    }

}
