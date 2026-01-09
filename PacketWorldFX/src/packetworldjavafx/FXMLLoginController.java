
package packetworldjavafx;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import packetworldjavafx.dominio.InicioSesionImp;
import packetworldjavafx.dto.Mensaje;
import packetworldjavafx.dto.RespuestaInicioSesion;
import packetworldjavafx.pojo.Colaborador;
import packetworldjavafx.utilidades.Alertas;
import packetworldjavafx.utilidades.PasswordHasher;

public class FXMLLoginController implements Initializable {
    
    double xMouse,yMouse;

    @FXML
    private TextField tfNumeroPersonal;
    @FXML
    private Label lbErrorPersonal;
    @FXML
    private PasswordField tfContrasena;
    @FXML
    private Label lbErrorPassword;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnLogin(ActionEvent event) {
        
        String numPersonal,passwordPlano;
            numPersonal = tfNumeroPersonal.getText();
            passwordPlano = tfContrasena.getText();
        
        if(validarCampos(numPersonal,passwordPlano)){
            String passwordHash = "";
            try {
                passwordHash = PasswordHasher.hash(passwordPlano);
                System.out.println(passwordHash);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                Alertas.mostrarAlertaSimple("Error", "Fallo en empaquetado de credenciales", Alert.AlertType.ERROR);
            }
            validarCredenciales(numPersonal,passwordHash);
        }
        
        
    }
    
    private boolean validarCampos(String numPersonal, String password){
        lbErrorPersonal.setVisible(false);
        lbErrorPassword.setVisible(false);
        boolean camposValidos = true;
        lbErrorPersonal.setText("");
        lbErrorPassword.setText("");
        if(numPersonal.isEmpty()){
                camposValidos = false ;
                lbErrorPersonal.setText("Numero de Personal obligatorio");
                lbErrorPersonal.setVisible(true);
        }
        if(password.isEmpty()){
            camposValidos = false ;
                lbErrorPassword.setText("Contraseña obligatoria");
                lbErrorPassword.setVisible(true);
        }
        return camposValidos;
    }

    private void validarCredenciales(String numPersonal, String password) {
        Mensaje respuesta = InicioSesionImp.verificarCredenciales(numPersonal,password);
            Colaborador colaborador = new Colaborador();
            Gson gson = new Gson();
            
            if(!respuesta.getError()){
                Alertas.mostrarAlertaSimple(
                        "Credenciales correctas",
                        "Bienvenido(a) colaborador(a) " ,
                        Alert.AlertType.INFORMATION);    
                irPantallaPrincipal(colaborador);
            }else{
                Alertas.mostrarAlertaSimple(
                        "Credenciales incorrectas",
                        respuesta.getMensaje(),
                        Alert.AlertType.ERROR); 
            }
    }

    private void irPantallaPrincipal(Colaborador colaborador) {
        int rol = colaborador.getIdRol();
        switch(rol){
            case 1:
                cargarAdminHome(colaborador);
                break;
            case 2:
                cargarEjecutivoTiendaHome(colaborador);
                break; 
            default:;
        
        }
        
        
    }
    
    private void cargarAdminHome(Colaborador colaborador){
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLHomeAdmin.fxml"));
            Parent vista = cargador.load();
            FXMLHomeAdminController controlador = cargador.getController();
            controlador.inicializarDatos(colaborador);
            Scene scPrincipal = new Scene(vista);
            // Obtenemos el Stage actual
            Stage escenarioBase = Stage.class.cast(tfNumeroPersonal.getScene().getWindow());
            escenarioBase.setScene(scPrincipal);
            escenarioBase.setTitle("Pantalla Principal");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void cargarEjecutivoTiendaHome(Colaborador colaborador){
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLHomeEjecutivoTienda.fxml"));
            Parent vista = cargador.load();
            //FXMLHomeEjecutivoTiendaController controlador = cargador.getController();
            //controlador.inicializarDatos(colaborador);
            Scene scPrincipal = new Scene(vista);
            // Obtenemos el Stage actual
            Stage escenarioBase = Stage.class.cast(tfNumeroPersonal.getScene().getWindow());
            escenarioBase.setScene(scPrincipal);
            escenarioBase.setTitle("Pantalla Principal");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void cerrarVentana(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void arrastreVentana(MouseEvent event) {
        double x = event.getScreenX();
        double y = event.getScreenY();
        Stage escenario = (Stage) tfNumeroPersonal.getScene().getWindow();
        escenario.setX(x - xMouse);
        escenario.setY(y - yMouse);        
    }

    @FXML
    private void guardarCoordenadas(MouseEvent event) {
        xMouse = event.getX();
        yMouse = event.getY();
    }
    
}
