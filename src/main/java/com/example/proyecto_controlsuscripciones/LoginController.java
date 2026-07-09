package com.example.proyecto_controlsuscripciones;

import com.example.proyecto_controlsuscripciones.modelo.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cmbRol;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {
        if(cmbRol != null) {
            cmbRol.getItems().addAll("Administrador", "Cliente", "Invitado");
        }
    }

    @FXML
    private void login() {
        String iden = txtCorreo.getText().trim();
        String pass = txtPassword.getText().trim();
        String rol = cmbRol.getValue();

        if (iden.isEmpty() || pass.isEmpty() || rol == null) {
            mostrarAlerta("Campos vacíos", "Por favor llene todos los campos.", Alert.AlertType.WARNING);
            return;
        }

        Usuario user = usuarioDAO.buscarPorIdentificador(iden);

        if (user != null && Seguridad.validar(pass, user.getPassword())) {
            if (user.getRol().equals(rol)) {
                mostrarAlerta("Éxito", "Bienvenido " + user.getUsuario(), Alert.AlertType.INFORMATION);

            } else {
                mostrarAlerta("Error", "El rol seleccionado no es correcto.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Usuario/Correo o contraseña incorrectos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void registrar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registrar.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Registro de Usuario");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiarCampos() {
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