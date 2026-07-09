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
        cmbRol.getItems().addAll("Cliente", "Invitado");
    }

    @FXML
    private void registrarUsuario() {
        String user = txtUsuario.getText().trim();
        String mail = txtCorreo.getText().trim();
        String pass = txtPassword.getText().trim();
        String rol = cmbRol.getValue();


        if (user.isEmpty() || mail.isEmpty() || pass.isEmpty() || rol == null) {
            mostrarAlerta("Error de Validación", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }


        if (rol.equalsIgnoreCase("Administrador")) {
            mostrarAlerta("Acceso Denegado", "No sepermite registrar administradores.", Alert.AlertType.WARNING);
            return;
        }


        String passHash = Seguridad.generarHash(pass);
        Usuario nuevo = new Usuario(user, mail, passHash, rol);


        if (usuarioDAO.registrar(nuevo)) {
            mostrarAlerta("Éxito", "Usuario registrado correctamente como " + rol + ".", Alert.AlertType.INFORMATION);
            volverAlLogin();
        } else {
            mostrarAlerta("Error", "No se pudo registrar el usuario. Es posible que el nombre de usuario o correo ya existan.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void volverAlLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtener el Stage actual desde cualquier componente (ej. txtUsuario)
            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login - Control de Suscripciones");
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error de Sistema", "No se pudo cargar la vista de login.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
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