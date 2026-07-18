package com.example.proyecto_controlsuscripciones;

import com.example.proyecto_controlsuscripciones.modelo.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class RegistrarController {

    @FXML private TextField txtUsuario, txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cmbRol;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {
        cmbRol.getItems().addAll("Administrador", "Cliente", "Invitado");


        cmbRol.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("Invitado".equals(newValue)) {
                txtUsuario.setDisable(true);
                txtCorreo.setDisable(true);
                txtPassword.setDisable(true);


                txtUsuario.clear();
                txtCorreo.clear();
                txtPassword.clear();

                mostrarAlerta("Registro no permitido",
                        "Los usuarios invitados no requieren registro. Si deseas entrar como invitado, vuelve al Login.",
                        Alert.AlertType.WARNING);


            } else {

                txtUsuario.setDisable(false);
                txtCorreo.setDisable(false);
                txtPassword.setDisable(false);
            }
        });
    }

    @FXML
    private void registrarUsuario() {
        String rol = cmbRol.getValue();


        if ("Invitado".equals(rol)) {
            mostrarAlerta("Acción no válida", "No es posible registrar una cuenta de tipo Invitado.", Alert.AlertType.ERROR);
            return;
        }

        String user = txtUsuario.getText().trim();
        String mail = txtCorreo.getText().trim();
        String pass = txtPassword.getText().trim();

        if (user.isEmpty() || mail.isEmpty() || pass.isEmpty() || rol == null) {
            mostrarAlerta("Error de Validación", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        // contraseña
        if (pass.length() < 8) {
            mostrarAlerta(
                    "Contraseña inválida",
                    "La contraseña debe tener mínimo 8 caracteres.",
                    Alert.AlertType.WARNING);
            return;
        }

//  Cliente
        if (rol.equals("Cliente") && !mail.contains("@")) {
            mostrarAlerta(
                    "Correo inválido",
                    "El correo debe ser una direccion de correo valida.",
                    Alert.AlertType.WARNING);
            return;
        }

        if ("Administrador".equals(rol)) {
            if (!mail.toLowerCase().endsWith("@suscripciones.epn.edu.ec")) {
                mostrarAlerta("Correo inválido", "Solo administradores con correo @suscripciones.epn.edu.ec", Alert.AlertType.ERROR);
                return;
            }
        }

        String passHash = Seguridad.generarHash(pass);
        Usuario nuevo = new Usuario(user, mail, passHash, rol);

        if (usuarioDAO.registrar(nuevo)) {
            mostrarAlerta("Éxito", "Usuario registrado correctamente.", Alert.AlertType.INFORMATION);
            volverAlLogin();
        } else {
            mostrarAlerta("Error", "No se pudo registrar. El nombre o correo ya podrían estar en uso.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void volverAlLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) cmbRol.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login - Control de Suscripciones");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiarCampos() {
        txtUsuario.clear();
        txtCorreo.clear();
        txtPassword.clear();
        cmbRol.setValue(null);
        txtUsuario.setDisable(false);
        txtCorreo.setDisable(false);
        txtPassword.setDisable(false);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}