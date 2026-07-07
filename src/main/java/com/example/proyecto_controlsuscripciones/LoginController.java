package com.example.proyecto_controlsuscripciones;

import com.example.proyecto_controlsuscripciones.modelo.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cmbRol;
    @FXML private Label lbId;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {
        // Agregamos los 3 roles solicitados
        cmbRol.getItems().addAll("Administrador", "Estándar", "Invitado");
    }

    @FXML
    private void registrar() {
        String userStr = txtUsuario.getText();
        String mailStr = txtCorreo.getText();
        String passStr = txtPassword.getText();
        String rolStr = cmbRol.getValue();

        // Validación de campos vacíos
        if (userStr.isEmpty() || passStr.isEmpty() || rolStr == null) {
            mostrarAlerta("Error", "Faltan datos obligatorios para el registro", Alert.AlertType.ERROR);
            return;
        }

        // 1. Generar HASH de la contraseña
        String passwordHasheada = Seguridad.generarHash(passStr);

        // 2. Crear objeto y guardar
        Usuario nuevo = new Usuario(userStr, mailStr, passwordHasheada, rolStr);

        if (usuarioDAO.registrar(nuevo)) {
            mostrarAlerta("Registro", "¡Usuario guardado con éxito!", Alert.AlertType.INFORMATION);
            limpiarCampos();
        } else {
            mostrarAlerta("Error", "No se pudo registrar el usuario en la base de datos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void login() {
        String userStr = txtUsuario.getText();
        String passStr = txtPassword.getText();

        if (userStr.isEmpty() || passStr.isEmpty()) {
            mostrarAlerta("Login", "Por favor ingrese usuario y contraseña", Alert.AlertType.WARNING);
            return;
        }

        // 1. Buscar el usuario en la BD por nombre
        Usuario usuarioBD = usuarioDAO.buscarPorNombre(userStr);

        if (usuarioBD != null) {
            // 2. Comparar la clave ingresada (plana) contra el Hash guardado
            if (Seguridad.validar(passStr, usuarioBD.getPassword())) {

                // Mostrar datos en la interfaz
                lbId.setText("ID: " + usuarioBD.getId_usuario());

                mostrarAlerta("Login exitoso", "¡Acceso Correcto! Bienvenido " + usuarioBD.getUsuario(), Alert.AlertType.INFORMATION);
                // Aquí podrías redirigir a otra ventana si lo deseas
            } else {
                mostrarAlerta("Login fallido", "Mensaje Incorrecto: Contraseña fallida", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Login fallido", "Mensaje Incorrecto: Usuario no encontrado", Alert.AlertType.ERROR);
        }
    }

    private void limpiarCampos() {
        txtUsuario.clear();
        txtCorreo.clear();
        txtPassword.clear();
        cmbRol.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}