package com.example.proyecto_controlsuscripciones;

import com.example.proyecto_controlsuscripciones.modelo.Usuario;
import com.example.proyecto_controlsuscripciones.modelo.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientesController {


    @FXML
    private TextField txtID;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnMostrar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnSalirClientes;

    @FXML
    private TableView<Usuario> tblClientes;

    @FXML
    private TableColumn<Usuario,Integer> colID;

    @FXML
    private TableColumn<Usuario,String> colNombre;

    @FXML
    private TableColumn<Usuario,String> colCorreo;

    @FXML
    private TableColumn<Usuario,String> colRol;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();


    @FXML
    public void initialize() {

        colID.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

    }


    @FXML
    private void mostrar() {

        ObservableList<Usuario> lista =
                FXCollections.observableArrayList(usuarioDAO.listarUsuarios());

        tblClientes.setItems(lista);

    }


    @FXML
    private void buscar() {

        if (txtID.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Ingrese un ID.");
            alert.showAndWait();
            return;

        }

        Usuario usuario = usuarioDAO.buscarPorId(
                Integer.parseInt(txtID.getText()));

        if (usuario == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Usuario no encontrado.");
            alert.showAndWait();

            tblClientes.getItems().clear();

            return;

        }

        tblClientes.setItems(
                FXCollections.observableArrayList(usuario));

    }


    @FXML
    private void eliminar() {

        if (txtID.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Ingrese un ID.");
            alert.showAndWait();

            return;
        }

        Usuario usuario = usuarioDAO.buscarPorId(
                Integer.parseInt(txtID.getText()));

        if (usuario == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Usuario no encontrado.");
            alert.showAndWait();

            return;

        }

        if (usuario.getRol().equals("Administrador")) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("No se puede eliminar un administrador.");
            alert.showAndWait();

            return;

        }

        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);

        confirmar.setHeaderText(null);
        confirmar.setContentText("¿Eliminar este usuario?");

        if (confirmar.showAndWait().get() == ButtonType.OK) {

            usuarioDAO.eliminarUsuario(usuario.getId_usuario());

            mostrar();

            limpiar();

        }

    }

    @FXML
    private void limpiar() {

        txtID.clear();

        tblClientes.getItems().clear();

    }


    @FXML
    private void salir() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Salir");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro que desea regresar?");

            if (alert.showAndWait().get() == ButtonType.OK) {

                Stage stage = (Stage) btnSalirClientes.getScene().getWindow();
                stage.close();

            }
        }



}
