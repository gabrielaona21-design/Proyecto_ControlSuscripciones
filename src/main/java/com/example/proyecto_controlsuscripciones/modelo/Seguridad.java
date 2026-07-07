package com.example.proyecto_controlsuscripciones.modelo;

import org.mindrot.jbcrypt.BCrypt;

public class Seguridad {
    public static String generarHash(String plana){
        return BCrypt.hashpw(plana,BCrypt.gensalt());
    }
    public static boolean validar (String plana, String phash){
        return BCrypt.checkpw(plana,phash);
    }

}
