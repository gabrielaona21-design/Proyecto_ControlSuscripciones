package com.example.proyecto_controlsuscripciones;

import com.example.proyecto_controlsuscripciones.modelo.Servicio;
import com.example.proyecto_controlsuscripciones.modelo.Suscripcion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class SuscripcionesController {
    @FXML private TextField txtID;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cbxCategoria;
    @FXML private ComboBox<String> cbxPlan;
    @FXML private Spinner<Double> spPrecio;
    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpFechaRenovacion;
    @FXML private RadioButton rbActiva;
    @FXML private RadioButton rbSuspendida;
    @FXML private RadioButton rbCancelada;


    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnMostrar;
    @FXML private Button btnBuscar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnMostrarUsers;
    @FXML private Button btnSalir;

    @FXML private TableView<Suscripcion> tblSuscripciones;
    @FXML private TableColumn<Suscripcion,Integer> colID;
    @FXML private TableColumn<Suscripcion,String> colNombre;
    @FXML private TableColumn<Suscripcion,String> colCategoria;
    @FXML private TableColumn<Suscripcion,String> colPlan;
    @FXML private TableColumn<Suscripcion,Double> colPrecio;
    @FXML private TableColumn<Suscripcion,LocalDate> colFechaInicio;
    @FXML private TableColumn<Suscripcion,LocalDate> colFechaRenovacion;
    @FXML private TableColumn<Suscripcion,String> colEstado;
    @FXML private TableColumn<Suscripcion, Integer> colIdUsuario;
    private ObservableList<Suscripcion> listaSuscripciones;
    private Servicio servicio = new Servicio();
    private ToggleGroup grupoEstado = new ToggleGroup();


    // Datos del usuario que inició sesión
    private String rolUsuario;
    private int idUsuario;



    @FXML
    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("idSuscripcion"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPlan.setCellValueFactory(new PropertyValueFactory<>("plan"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colFechaRenovacion.setCellValueFactory(new PropertyValueFactory<>("fechaRenovacion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        listaSuscripciones = FXCollections.observableArrayList();
        tblSuscripciones.setItems(listaSuscripciones);
        cbxCategoria.getItems().addAll(
                "Streaming",
                "Música",
                "Videojuegos",
                "Software",
                "Almacenamiento",
                "Educación",
                "Otro"
        );
        cbxPlan.getItems().addAll(
                "Mensual",
                "Trimestral",
                "Anual"
        );

        //Consultado <- Método para el Spinner o Precio
        SpinnerValueFactory.DoubleSpinnerValueFactory precio =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(
                        0,
                        1000,
                        0,
                        0.01
                );
        spPrecio.setValueFactory(precio);
        //Método para hacer el Spinner Editable
        spPrecio.setEditable(true);
        rbActiva.setToggleGroup(grupoEstado);
        rbSuspendida.setToggleGroup(grupoEstado);
        rbCancelada.setToggleGroup(grupoEstado);

        tblSuscripciones.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if(newSelection != null){
                        txtID.setText(String.valueOf(newSelection.getIdSuscripcion()));
                        txtNombre.setText(newSelection.getNombre());
                        cbxCategoria.setValue(newSelection.getCategoria());
                        cbxPlan.setValue(newSelection.getPlan());
                        spPrecio.getValueFactory().setValue(newSelection.getPrecio());
                        dpFechaInicio.setValue(newSelection.getFechaInicio());
                        dpFechaRenovacion.setValue(newSelection.getFechaRenovacion());
                        switch (newSelection.getEstado()){
                            case "Activa":
                                rbActiva.setSelected(true);
                                break;
                            case "Suspendida":
                                rbSuspendida.setSelected(true);
                                break;
                            case "Cancelada":
                                rbCancelada.setSelected(true);
                                break;
                        }
                    }
                }
        );

        //Consultado <- Método para la fecha
        dpFechaInicio.valueProperty().addListener((obs, oldValue, newValue) -> {
            calcularFechaRenovacion();
        });

        cbxPlan.valueProperty().addListener((obs, oldValue, newValue) -> {
            calcularFechaRenovacion();
        });

        dpFechaRenovacion.valueProperty().addListener((obs, oldValue, newValue) -> {
            actualizarEstadoAutomatico();
        });
    }



    public void setDatosUsuario(String rol, int idUsuario) {

        this.rolUsuario = rol;
        this.idUsuario = idUsuario;

        aplicarPermisos();

        if ("Invitado".equals(rolUsuario)) {
            listaSuscripciones.clear();
        } else {
            obtenerRegistrosTabla();
        }
    }

    private void aplicarPermisos() {

        switch (rolUsuario) {

            case "Cliente":

                btnActualizar.setVisible(true);
                btnEliminar.setVisible(false);
                btnAgregar.setVisible(true);
                btnBuscar.setVisible(true);
                btnMostrar.setVisible(true);
                btnLimpiar.setVisible(true);
                btnMostrarUsers.setVisible(false);
                colIdUsuario.setVisible(false);

                break;

            case "Invitado":

                btnActualizar.setVisible(false);
                btnEliminar.setVisible(false);
                btnAgregar.setVisible(false);
                btnBuscar.setVisible(true);
                btnMostrar.setVisible(false);
                btnLimpiar.setVisible(true);
                btnMostrarUsers.setVisible(false);
                colIdUsuario.setVisible(false);

                break;
        }

    }


    //mostar los clientes admin
    @FXML
    private void abrirClientes() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("clientes.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) btnMostrarUsers.getScene().getWindow();

            stage.setTitle("Usuarios Activos");

            stage.setScene(scene);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //cerrar sesion

    @FXML
    private void cerrarSesion() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Cerrar sesión");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro que desea cerrar sesión?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            try {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("login.fxml"));

                Scene scene = new Scene(loader.load());

                Stage stage = (Stage) btnSalir.getScene().getWindow();

                stage.setScene(scene);
                stage.setTitle("Login");

                stage.show();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }


    @FXML
    public void obtenerRegistrosTabla(){
        listaSuscripciones.clear();
        Map<Integer,Suscripcion> mapa;
        if (rolUsuario != null && (rolUsuario.equals("Cliente") || rolUsuario.equals("Invitado"))) {
            mapa = servicio.seleccionarPorUsuario(idUsuario);
        } else {
            mapa = servicio.seleccionarTodo();
        }
        for(Map.Entry<Integer,Suscripcion> entry : mapa.entrySet()){
            listaSuscripciones.add(entry.getValue());
        }
        limpiarCampos();
    }

    public void limpiarCampos() {
        txtID.setText("");
        txtNombre.setText("");
        cbxCategoria.setValue(null);
        cbxPlan.setValue(null);
        spPrecio.getValueFactory().setValue(0.0);
        dpFechaInicio.setValue(null);
        dpFechaRenovacion.setValue(null);
        rbActiva.setSelected(false);
        rbSuspendida.setSelected(false);
        rbCancelada.setSelected(false);
    }

     @FXML
     public void insertar(){
         try{
             String estado="";
             if(rbActiva.isSelected())
                 estado="Activa";
             else if(rbSuspendida.isSelected())
                 estado="Suspendida";
             else
                 estado="Cancelada";
             Suscripcion suscripcion = new Suscripcion(
                     txtNombre.getText(),
                     cbxCategoria.getValue(),
                     cbxPlan.getValue(),
                     spPrecio.getValue(),
                     dpFechaInicio.getValue(),
                     dpFechaRenovacion.getValue(),
                     estado, this.idUsuario
             );
             servicio.insertar(suscripcion);
             obtenerRegistrosTabla();
         }catch(Exception e){
             Alert alerta = new Alert(Alert.AlertType.ERROR);
             alerta.setTitle("Error");
             alerta.setHeaderText("Datos inválidos");
             alerta.setContentText("Verifique la información ingresada.");
             alerta.showAndWait();
         }
     }

     @FXML
     public void modificar(){
         try{
             String estado="";
             if(rbActiva.isSelected())
                 estado="Activa";
             else if(rbSuspendida.isSelected())
                 estado="Suspendida";
             else
                 estado="Cancelada";
             Suscripcion suscripcion = new Suscripcion(
                     Integer.parseInt(txtID.getText()),
                     txtNombre.getText(),
                     cbxCategoria.getValue(),
                     cbxPlan.getValue(),
                     spPrecio.getValue(),
                     dpFechaInicio.getValue(),
                     dpFechaRenovacion.getValue(),
                     estado, this.idUsuario
             );
             servicio.actualizar(suscripcion);
             obtenerRegistrosTabla();
         }catch(Exception e){
             Alert alerta = new Alert(Alert.AlertType.ERROR);
             alerta.setTitle("Error");
             alerta.setHeaderText("No se pudo actualizar");
             alerta.setContentText("Seleccione una suscripción válida.");
             alerta.showAndWait();
         }
     }

    @FXML
    public void eliminar(){
        try{
            int id = Integer.parseInt(txtID.getText());
            servicio.eliminar(id);
            obtenerRegistrosTabla();
            limpiarCampos();
        }catch(Exception e){
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Sin selección");
            alerta.setContentText("Seleccione una suscripción.");
            alerta.showAndWait();
        }
    }

    @FXML
    public void buscar(){
        try{
            int id = Integer.parseInt(txtID.getText());
            Suscripcion suscripcion = servicio.buscar(id);
            if(suscripcion!=null){
                listaSuscripciones.clear();
                listaSuscripciones.add(suscripcion);
                txtNombre.setText(suscripcion.getNombre());
                cbxCategoria.setValue(suscripcion.getCategoria());
                cbxPlan.setValue(suscripcion.getPlan());
                spPrecio.getValueFactory().setValue(suscripcion.getPrecio());
                dpFechaInicio.setValue(suscripcion.getFechaInicio());
                dpFechaRenovacion.setValue(suscripcion.getFechaRenovacion());
                switch (suscripcion.getEstado()){
                    case "Activa":
                        rbActiva.setSelected(true);
                        break;
                    case "Suspendida":
                        rbSuspendida.setSelected(true);
                        break;
                    case "Cancelada":
                        rbCancelada.setSelected(true);
                        break;
                }
            }else{
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("No encontrado");
                alerta.setHeaderText(null);
                alerta.setContentText("No existe una suscripción con ese ID.");
                alerta.showAndWait();
            }
        }catch(Exception e){
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("Ingrese un ID válido.");
            alerta.showAndWait();
        }
    }

    @FXML
    public void mostrar(){
        obtenerRegistrosTabla();
    }

    @FXML
    public void limpiar(){
        limpiarCampos();
    }

    //Métodos para la fecha
    private void calcularFechaRenovacion() {

        if (dpFechaInicio.getValue() == null || cbxPlan.getValue() == null)
            return;

        LocalDate fecha = dpFechaInicio.getValue();

        switch (cbxPlan.getValue()) {

            case "Mensual":
                dpFechaRenovacion.setValue(fecha.plusMonths(1));
                break;

            case "Trimestral":
                dpFechaRenovacion.setValue(fecha.plusMonths(3));
                break;

            case "Anual":
                dpFechaRenovacion.setValue(fecha.plusYears(1));
                break;
        }

        actualizarEstadoAutomatico();
    }

    private void actualizarEstadoAutomatico() {

        if (dpFechaRenovacion.getValue() == null)
            return;

        if (dpFechaRenovacion.getValue().isBefore(LocalDate.now())) {

            rbSuspendida.setSelected(true);

        } else {

            rbActiva.setSelected(true);

        }

    }
}
