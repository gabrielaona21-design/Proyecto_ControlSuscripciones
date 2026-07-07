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
        // Llenamos el combo con las opciones que definiste
        cmbRol.getItems().addAll("Administrador", "Estándar", "Invitado");
    }

    @FXML
    private void registrar() {
        // 1. Obtener datos de la interfaz
        String userStr = txtUsuario.getText();
        String mailStr = txtCorreo.getText();
        String passStr = txtPassword.getText();
        String rolStr = cmbRol.getValue();

        if (userStr.isEmpty() || passStr.isEmpty() || rolStr == null) {
            mostrarAlerta("Error", "Faltan datos obligatorios", Alert.AlertType.ERROR);
            return;
        }

        // 2. Hashear la contraseña con tu clase Seguridad
        String passwordHasheada = Seguridad.generarHash(passStr);

        // 3. Crear objeto Usuario y guardarlo
        Usuario nuevo = new Usuario();
        nuevo.setUsuario(userStr);
        nuevo.setCorreo(mailStr);
        nuevo.setPassword(passwordHasheada);
        nuevo.setRol(rolStr);

        if (usuarioDAO.registrar(nuevo)) {
            mostrarAlerta("Registro", "Usuario guardado con éxito", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No se pudo registrar el usuario", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void login() {
        String userStr = txtUsuario.getText();
        String passStr = txtPassword.getText();

        // 1. Buscar el usuario en la BD
        Usuario usuarioBD = usuarioDAO.buscarPorNombre(userStr);

        if (usuarioBD != null) {
            // 2. Validar contraseña usando Seguridad.validar (compara plana vs hash)
            if (Seguridad.validar(passStr, usuarioBD.getPassword())) {

                // Mostrar ID y Rol en la interfaz como prueba
                lbId.setText("ID: " + usuarioBD.getId_usuario());

                mostrarAlerta("Login", "¡Mensaje Correcto!", Alert.AlertType.INFORMATION);

                // Aquí podrías abrir la siguiente ventana
            } else {
                mostrarAlerta("Login", "Mensaje Incorrecto: Contraseña fallida", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Login", "Mensaje Incorrecto: Usuario no encontrado", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
