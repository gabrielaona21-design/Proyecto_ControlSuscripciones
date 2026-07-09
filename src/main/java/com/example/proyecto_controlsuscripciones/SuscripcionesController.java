package com.example.proyecto_controlsuscripciones;

import com.example.proyecto_controlsuscripciones.modelo.Servicio;
import com.example.proyecto_controlsuscripciones.modelo.Suscripcion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    @FXML private TableView<Suscripcion> tblSuscripciones;
    @FXML private TableColumn<Suscripcion,Integer> colID;
    @FXML private TableColumn<Suscripcion,String> colNombre;
    @FXML private TableColumn<Suscripcion,String> colCategoria;
    @FXML private TableColumn<Suscripcion,String> colPlan;
    @FXML private TableColumn<Suscripcion,Double> colPrecio;
    @FXML private TableColumn<Suscripcion,LocalDate> colFechaInicio;
    @FXML private TableColumn<Suscripcion,LocalDate> colFechaRenovacion;
    @FXML private TableColumn<Suscripcion,String> colEstado;
    private ObservableList<Suscripcion> listaSuscripciones;
    private Servicio servicio = new Servicio();
    private ToggleGroup grupoEstado = new ToggleGroup();

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
        SpinnerValueFactory.DoubleSpinnerValueFactory precio =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(
                        0,
                        1000,
                        0,
                        0.5
                );
        spPrecio.setValueFactory(precio);
        rbActiva.setToggleGroup(grupoEstado);
        rbSuspendida.setToggleGroup(grupoEstado);
        rbCancelada.setToggleGroup(grupoEstado);
        obtenerRegistrosTabla();
    }

    @FXML
    public void obtenerRegistrosTabla(){
        listaSuscripciones.clear();
        Map<Integer,Suscripcion> mapa =
                servicio.seleccionarTodo();
        for(Map.Entry<Integer,Suscripcion> entry : mapa.entrySet()){
            listaSuscripciones.add(entry.getValue());
        }
        limpiarCampos();
    }


}
