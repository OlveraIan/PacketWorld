package packetworldjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import packetworldjavafx.dominio.ColaboradorImp;
import packetworldjavafx.dto.Respuesta;
import packetworldjavafx.interfaz.INotificador;
import packetworldjavafx.pojo.Colaborador;
import packetworldjavafx.pojo.Rol;
import packetworldjavafx.utilidades.Alertas;

public class FXMLFormularioColaboradorController implements Initializable {

    private File imagenSeleccionada;
    private Colaborador colaborador;
    private INotificador observador;

    @FXML
    private TextField tfNp;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfAm;
    public Label lColaborador;
    @FXML
    private TextField tfCurp;
    @FXML
    private ComboBox<Rol> cbRol;

    @FXML
    private TextField tfContrasena;

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfAp;
    @FXML
    public Button btnAccion;
    @FXML
    private Pane pane;
    @FXML
    private ImageView imagenperfil;
    @FXML
    private TextField tfNulic;
    @FXML
    private Label lNuLic;

    @FXML
    void guardar(ActionEvent event) {

        Colaborador c = new Colaborador();
        c.setNombre(tfNombre.getText());
        c.setApellidoPaterno(tfAp.getText());
        c.setApellidoMaterno(tfAm.getText());
        c.setCorreoElectronico(tfCorreo.getText());
        c.setCurp(tfCurp.getText());
        c.setPassword(tfContrasena.getText());
        c.setNumeroPersonal(tfNp.getText());

        if (!tfNulic.getText().isEmpty()) {
            c.setNumLicencia(tfNulic.getText());
        }

        if (cbRol.getValue() != null) {
            c.setIdRol(cbRol.getValue().getIdRol());
        }

        if (colaborador == null) {
            guardarColaborador(c);
        } else {
            c.setIdColaborador(colaborador.getIdColaborador());
            editarColaborador(c);
        }

    }

    @FXML
    private void btnSeleccionarFoto(ActionEvent event) {
        FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una imagen");

        // Configurar el filtro para archivos de imagen
        FileChooser.ExtensionFilter filtroImg = new FileChooser.ExtensionFilter("Archivos de imagen (*.png, *jpg, *jpeg)", "*.PNG", "*.JPG", "*.JPEG");
        dialogoSeleccionImg.getExtensionFilters().add(filtroImg);

        Stage stageActual = (Stage) tfNombre.getScene().getWindow();
        imagenSeleccionada = dialogoSeleccionImg.showOpenDialog(stageActual);

        if (imagenSeleccionada != null) {
            mostrarFotografiaSeleccionada(imagenSeleccionada);
        }
    }

    private void btnSubirFotografia(ActionEvent event) {
        if (imagenSeleccionada != null) {
            cargarFotografiaServidor(imagenSeleccionada); // Enviar la imagen al servidor
        } else {
            Alertas.mostrarAlertaSimple("Selecciona fotografía", "Para subir una fotografía del Colaborador debes seleccionarla previamente", Alert.AlertType.WARNING);
        }
    }

    private void cargarFotografiaServidor(File imagen) {
        try {
            byte[] imgBytes = Files.readAllBytes(imagen.toPath()); // Leer los bytes de la imagen
            Respuesta msj = ColaboradorImp.subirFotografiaColaborador(colaborador.getIdColaborador(), imgBytes);

            if (!msj.isError()) {
                Alertas.mostrarAlertaSimple("Fotografía enviada", msj.getMensaje(), Alert.AlertType.INFORMATION);
            } else {
                Alertas.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        } catch (IOException ex) {
            Alertas.mostrarAlertaSimple("Error", "Error: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarFotografiaSeleccionada(File img) {
        try {
            BufferedImage buffer = ImageIO.read(img);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            imagenperfil.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void obtenerImagenServicio() {
        Colaborador ColaboradorFoto = ColaboradorImp.obtenerFotografiaColaborador(colaborador.getIdColaborador());

        if (ColaboradorFoto != null && ColaboradorFoto.getFoto() != null) {
            Image image;
            image = new Image(new ByteArrayInputStream(ColaboradorFoto.getFoto()));
        }
    }

    private void mostrarFotografiaServidor(String imgBase64) {
        byte[] foto = Base64.getDecoder().decode(imgBase64.replaceAll("\\n", ""));
        Image image = new Image(new ByteArrayInputStream(foto));
        imagenperfil.setImage(image);
    }

    private void cargarDatos() {
        tfNombre.setText(colaborador.getNombre());
        tfAp.setText(colaborador.getApellidoPaterno());
        tfAm.setText(colaborador.getApellidoMaterno());
        tfCorreo.setText(colaborador.getCorreoElectronico());
        tfCurp.setText(colaborador.getCurp());
        tfContrasena.setText(colaborador.getPassword());
        tfNp.setText(colaborador.getNumeroPersonal());
        if (colaborador.getNumLicencia() != null) {
            tfNulic.setText(colaborador.getNumLicencia());
        }

        // Mostrar el rol del colaborador si está asignado
        if (colaborador.getIdRol() != 0) {
            for (Rol rol : cbRol.getItems()) {
                if (rol.getIdRol() == colaborador.getIdRol()) {
                    cbRol.setValue(rol);
                    break;
                }
            }
        }

        pane.setVisible(true);
        obtenerImagenServicio();
    }

    public void inicializaInformacion(Colaborador colaborador, INotificador observador) {
        this.colaborador = colaborador;
        this.observador = observador;
        cargarRoles();
        tfNulic.setDisable(true);
        if (colaborador != null) {
            cargarDatos();
            cbRol.setDisable(true);
            tfNp.setDisable(true);
        } else {
            pane.setVisible(false);
        }
    }

    private void guardarColaborador(Colaborador colaborador) {
        Respuesta msj = ColaboradorImp.registrarColaborador(colaborador);
        if (!msj.isError()) {
            Alertas.mostrarAlertaSimple("Colaborador registrado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notify();
            cerrarVentana();
        } else {
            Alertas.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarColaborador(Colaborador colaborador) {
        Respuesta msj = ColaboradorImp.editarColaborador(colaborador);
        if (!msj.isError()) {
            Alertas.mostrarAlertaSimple("Colaborador editado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notify();
            cerrarVentana();
        } else {
            Alertas.mostrarAlertaSimple("Error al editar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void cerrarVentana() {
        Stage escenario = (Stage) tfNombre.getScene().getWindow();
        escenario.close();
    }

    private void cargarRoles() {
        ObservableList<Rol> roles = FXCollections.observableArrayList();
        List<Rol> info = ColaboradorImp.roles();
        roles.addAll(info);
        cbRol.setItems(roles);
    }

    private boolean validarCorreo(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return correo.matches(regex);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfNulic.setDisable(false);
        lNuLic.setDisable(false); // Mostrar el campo "Número de Licencia"
        // Listener para mostrar u ocultar el campo de "Número de Licencia" dependiendo del rol seleccionado
        cbRol.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.getNombre().equals("Conductor")) {
                    tfNulic.setDisable(false);
                    lNuLic.setDisable(false); // Mostrar el campo "Número de Licencia"
                } else {
                    tfNulic.setDisable(true);
                    lNuLic.setDisable(true); // Ocultar el campo "Número de Licencia"
                }
            }
        });
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }
}
