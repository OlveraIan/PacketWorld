/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetworldjavafx;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import packetworldjavafx.dominio.ColaboradorImp;
import packetworldjavafx.dto.Respuesta;
import packetworldjavafx.pojo.Colaborador;
import packetworldjavafx.utilidades.Alertas;

/**
 * FXML Controller class
 *
 * @author ian_e
 */
public class FXMLHomeAdminController implements Initializable {
    /*--------------------------------------------------------*/
    //TABLAS
    /*--------------------------------------------------------*/
    //TABLA COLABORADOR
    @FXML
    private TableView<Colaborador> tablaColaboradores;
    @FXML
    private TableColumn numeroPersonal;
    @FXML
    private TableColumn nombreColaborador;
    @FXML
    private TableColumn apellidoPaterno;
    @FXML
    private TableColumn apellidoMaterno;
    @FXML
    private TableColumn CURP;
    @FXML
    private TableColumn correoElectronico;
    @FXML
    private TableColumn rol;
    @FXML
    private TableColumn sucursal;
    @FXML
    private TableColumn numeroLicencia;
    @FXML
    private TableColumn activo;
    /*--------------------------------------------------------*/
    //TABLA UNIDAD
    @FXML
    private TableView<?> tablaUnidades;
    @FXML
    private TableColumn idInterno;
    @FXML
    private TableColumn VIN;
    @FXML
    private TableColumn marca;
    @FXML
    private TableColumn modelo;
    @FXML
    private TableColumn año;
    @FXML
    private TableColumn propulsion;
    @FXML
    private TableColumn estadoUnidad;
    @FXML
    private TableColumn conductorDesignado;
    /*---------------------------------------------------------*/
    //TABLA SUCURSAL
    @FXML
    private TableView<?> tablaSucursales;
    @FXML
    private TableColumn idSucursal;
    @FXML
    private TableColumn nombreSucursal;
    @FXML
    private TableColumn estadoSucursal;
    @FXML
    private TableColumn pais;
    @FXML
    private TableColumn codigoPostal;
    @FXML
    private TableColumn calle;
    @FXML
    private TableColumn numero;
    @FXML
    private TableColumn colonia;
    @FXML
    private TableColumn ciudad;
    @FXML
    private TableColumn estado;
    /*---------------------------------------------------------*/
    //TABLA ENVIO
    @FXML
    private TableView<?> tablaEnvios;
    @FXML
    private TableColumn numeroGuia;
    @FXML
    private TableColumn correoElectronicoCliente;
    @FXML
    private TableColumn nombreRecibe;
    @FXML
    private TableColumn sucursalOrigen;
    @FXML
    private TableColumn destino;
    @FXML
    private TableColumn costo;
    @FXML
    private TableColumn estadoEnvio;
    @FXML
    private TableColumn colaboradorIdRef;
    /*---------------------------------------------------*/
    //TABLA CLIENTE
    @FXML
    private TableView<?> tablaClientes;
    @FXML
    private TableColumn emailCliente;
    @FXML
    private TableColumn nombreCliente;
    @FXML
    private TableColumn apellidoPaternoCliente;
    @FXML
    private TableColumn apellidoMaternoCliente;
    @FXML
    private TableColumn telefonoCliente;
    @FXML
    private TableColumn calleCliente;
    @FXML
    private TableColumn numeroCliente;
    @FXML
    private TableColumn coloniaCliente;
    @FXML
    private TableColumn codigoPostalCliente;
    /*----------------------------------------------------------*/
    
    @FXML
    private Label lbNombreColaborador;
    
    private ObservableList<Colaborador> observadorColaboradores; 
    

    //LOGICA DE INICIO
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    void inicializarDatos(Colaborador colaborador) {
        lbNombreColaborador.setText(colaborador.getNombre()+" "+colaborador.getApellidoPaterno());
    }
    private void configurarTablas() {
        //Colaborador
        numeroPersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        nombreColaborador.setCellValueFactory(new PropertyValueFactory("nombre"));
        apellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        apellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        CURP.setCellValueFactory(new PropertyValueFactory("CURP"));
        correoElectronico.setCellValueFactory(new PropertyValueFactory("correoElectronico"));
        rol.setCellValueFactory(new PropertyValueFactory("nombreRol"));
        sucursal.setCellValueFactory(new PropertyValueFactory("nombreSucursal"));
        numeroLicencia.setCellValueFactory(new PropertyValueFactory("numeroLicencia"));
        activo.setCellValueFactory(new PropertyValueFactory("activo"));;
        //UNIDAD
        idInterno.setCellValueFactory(new PropertyValueFactory("idInterno"));
        VIN.setCellValueFactory(new PropertyValueFactory("VIN"));
        marca.setCellValueFactory(new PropertyValueFactory("marca"));
        modelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        propulsion.setCellValueFactory(new PropertyValueFactory("propulsion"));
        año.setCellValueFactory(new PropertyValueFactory("año"));
        estadoUnidad.setCellValueFactory(new PropertyValueFactory("estado"));
        conductorDesignado.setCellValueFactory(new PropertyValueFactory("conductorDesignado"));
        //SUCURSAL
        idSucursal.setCellValueFactory(new PropertyValueFactory("idSucursal"));
        nombreSucursal.setCellValueFactory(new PropertyValueFactory("nombre"));
        estadoSucursal.setCellValueFactory(new PropertyValueFactory("estadoSucursal"));
        pais.setCellValueFactory(new PropertyValueFactory("pais"));
        codigoPostal.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
        calle.setCellValueFactory(new PropertyValueFactory("calle"));
        numero.setCellValueFactory(new PropertyValueFactory("numero"));
        colonia.setCellValueFactory(new PropertyValueFactory("colonia"));
        ciudad.setCellValueFactory(new PropertyValueFactory("ciudad"));
        estado.setCellValueFactory(new PropertyValueFactory("estado"));        
        //ENVIO
        numeroGuia.setCellValueFactory(new PropertyValueFactory("numeroGuia"));
        correoElectronicoCliente.setCellValueFactory(new PropertyValueFactory("correoElectronicoCliente"));
        nombreRecibe.setCellValueFactory(new PropertyValueFactory("nombreRecibe"));
        sucursalOrigen.setCellValueFactory(new PropertyValueFactory("sucursalOrigen"));
        destino.setCellValueFactory(new PropertyValueFactory("destino"));
        costo.setCellValueFactory(new PropertyValueFactory("costo"));
        estadoEnvio.setCellValueFactory(new PropertyValueFactory("estado"));
        colaboradorIdRef.setCellValueFactory(new PropertyValueFactory("numeroLicencia"));
        //CLIENTE
        emailCliente.setCellValueFactory(new PropertyValueFactory("emailCliente"));
        nombreCliente.setCellValueFactory(new PropertyValueFactory("nombreCliente"));
        apellidoPaternoCliente.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        apellidoMaternoCliente.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        telefonoCliente.setCellValueFactory(new PropertyValueFactory("telefono"));
        calleCliente.setCellValueFactory(new PropertyValueFactory("calle"));
        numeroCliente.setCellValueFactory(new PropertyValueFactory("numero"));
        coloniaCliente.setCellValueFactory(new PropertyValueFactory("colonia"));
        codigoPostalCliente.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
    }
    
    //TAB INICIO
    @FXML
    private void cambioTabInicio(Event event) {
    }
    /*----------------------------------------------------------*/
    //TAB COLABORADOR
    @FXML
    private TextField tfBusquedaColaboradores;

    @FXML
    private void clickBuscarColab(ActionEvent event) {
    }

    @FXML
    private void clickEditarColab(ActionEvent event) {
    }

    @FXML
    private void clickEliminarColab(ActionEvent event) {
            Colaborador colaborador = tablaColaboradores.getSelectionModel().getSelectedItem();
        if (colaborador != null) {
            boolean confirmacion = Alertas.mostrarAlertaConfirmacion("Eliminar el profesor", "¿Deseas eliminar el colaborador: " + colaborador.getNombre() + "?"
                    + "\nEsta operacion es irreversible");
            if (confirmacion) {
                eliminarColaborador(colaborador);
                cargarInformacionColaboradores();
            }
        } else {
            Alertas.mostrarAlertaSimple("Selecciona un colaborador", "Para eliminar un colaborador, debes seleccionarlo de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clickCambiarEstadoColab(ActionEvent event) {
    }

    @FXML
    private void clickAñadirColab(ActionEvent event) {
    }
    
    @FXML
    private void cambioTabColaboradores(Event event) {
        
    }
    /*-----------------------------------------------------*/
    //TAB UNIDAD
    @FXML
    private TextField tfBusquedaUnidades;

    @FXML
    private void clickBuscarUnidad(ActionEvent event) {
    }

    @FXML
    private void clickEditarUnidad(ActionEvent event) {
    }

    @FXML
    private void clickEliminarUnidad(ActionEvent event) {
    }

    @FXML
    private void clickDesasignarConductor(ActionEvent event) {
    }

    @FXML
    private void clickAsignarConductor(ActionEvent event) {
    }

    @FXML
    private void clickAñadirUnidad(ActionEvent event) {
    }

    @FXML
    private void cambioTabUnidades(Event event) {
    }
    /*-----------------------------------------------------*/
    //TAB SUCURSAL
    @FXML
    private TextField tfBusquedaSucursales;
    @FXML
    private void clickBuscarSucursal(ActionEvent event) {
    }

    @FXML
    private void clickEditarSucursal(ActionEvent event) {
    }

    @FXML
    private void clickEliminarSucursal(ActionEvent event) {
    }

    @FXML
    private void clickDarDeBajaSucursal(ActionEvent event) {
    }

    @FXML
    private void clickAñadirSucursal(ActionEvent event) {
    }

    @FXML
    private void cambioTabSucursales(Event event) {
    }
    /*-----------------------------------------------------*/
    //TAB ENVIO
    @FXML
    private TextField tfBusquedaEnvios;

    @FXML
    private void clickBuscarEnvio(ActionEvent event) {
    }

    @FXML
    private void clickEditarEnvio(ActionEvent event) {
    }

    @FXML
    private void clickEliminarEnvio(ActionEvent event) {
    }

    @FXML
    private void clickAñadirEnvio(ActionEvent event) {
    }

    @FXML
    private void cambioTabEnvios(Event event) {
    }

    @FXML
    private void clickCerrarSesion(ActionEvent event) {
    }
    /*-------------------------------------------------*/
    //TAB CLIENTE
    @FXML
    private TextField tfBusquedaClientes;
    @FXML
    private void clickBuscarCliente(ActionEvent event) {
    }

    @FXML
    private void clickEditarCliente(ActionEvent event) {
    }

    @FXML
    private void clickEliminarCliente(ActionEvent event) {
    }

    @FXML
    private void clickAñadirCliente(ActionEvent event) {
    }

    @FXML
    private void cambioTabClientes(Event event) {
    }
    
    /*-------------------------------------------------*/

    private void cargarInformacionColaboradores() {
        HashMap<String, Object> respuesta = ColaboradorImp.obtenerTodos();
        boolean esError = (boolean) respuesta.get("error");
        if (!esError) {
            List<Colaborador> profesoresAPI = (List<Colaborador>) respuesta.get("colaboradores");
            observadorColaboradores = FXCollections.observableArrayList();
            observadorColaboradores.addAll(profesoresAPI);
            tablaColaboradores.setItems(observadorColaboradores);
        } else {
            Alertas.mostrarAlertaSimple("Error al cargar",
                    "" + respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    private void eliminarColaborador(Colaborador colaborador) {
        Respuesta respuesta = ColaboradorImp.eliminar(colaborador);
        if (!respuesta.isError()) {
            Alertas.mostrarAlertaSimple("Informacion eliminada", "La informacion de colaborador(a) " + colaborador.getNombre() + "ha sido elminidada correctamente.", Alert.AlertType.INFORMATION);
        } else {
            Alertas.mostrarAlertaSimple("Error al elminar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    

    
}
