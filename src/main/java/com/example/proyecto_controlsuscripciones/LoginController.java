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
        cmbRol.getItems().addAll("Administrador", "Estándar", "Invitado");
    }

    @FXML
    private void registrar() {
        String userStr = txtUsuario.getText();
        String mailStr = txtCorreo.getText();
        String passStr = txtPassword.getText();
        String rolStr = cmbRol.getValue();

        if (userStr.isEmpty() || passStr.isEmpty() || rolStr == null) {
            mostrarAlerta("Error", "Faltan datos obligatorios para el registro", Alert.AlertType.ERROR);
            return;
        }


        String passwordHasheada = Seguridad.generarHash(passStr);


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
        String userStr = txtUsuario.getText().trim();
        String passStr = txtPassword.getText().trim();
        String rolSeleccionado = cmbRol.getValue();


        if (userStr.isEmpty() || passStr.isEmpty() || rolSeleccionado == null) {
            mostrarAlerta("Campos Incompletos", "Por favor, ingrese usuario, contraseña y seleccione su Rol.", Alert.AlertType.WARNING);
            return;
        }


        Usuario usuarioBD = usuarioDAO.buscarPorNombre(userStr);

        if (usuarioBD != null) {

            if (Seguridad.validar(passStr, usuarioBD.getPassword())) {


                if (usuarioBD.getRol().equals(rolSeleccionado)) {

                    // Si todo es correcto:
                    lbId.setText("ID: " + usuarioBD.getId_usuario());
                    mostrarAlerta("Acceso Correcto", "¡Bienvenido " + usuarioBD.getUsuario() + "! Ingresando como " + rolSeleccionado, Alert.AlertType.INFORMATION);



                } else {
                    mostrarAlerta("Error de Rol", "El rol seleccionado no corresponde a este usuario.", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Login Fallido", "Usuario o Contraseña incorrectos.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Login Fallido", "El usuario no existe.", Alert.AlertType.ERROR);
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