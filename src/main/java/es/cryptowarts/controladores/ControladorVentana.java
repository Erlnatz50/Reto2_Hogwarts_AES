
package es.cryptowarts.controladores;

import es.cryptowarts.cifrado.CifradoAES;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 */
public class ControladorVentana {

    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ControladorVentana.class);

    /**
     * Bundle del sistema de internacionalización
     */
    private ResourceBundle bundle;

    /**
     *
     */
    @FXML
    private Label lblArchivo;

    /**
     *
     */
    @FXML
    private Label lblMensaje;

    /**
     *
     */
    @FXML
    private TextArea txtDcha;

    /**
     *
     */
    @FXML
    private TextArea txtIzda;

    /**
     *
     */
    @FXML
    private ComboBox<String> cmbOpcion;

    /**
     *
     */
    @FXML
    private Button btnAreas;

    /**
     *
     */
    @FXML
    private Button btnLimpiarAreas;

    /**
     *
     */
    @FXML
    private Button btnSelecFichero;

    /**  */
    private static final String CIFRAR = "cifrar";

    /**  */
    private static final String DESCIFRAR = "descifrar";

    /**  */
    private static final String SELECCIONA_OPCION = "seleccionaOpcion";


    @FXML
    public void initialize() {
        bundle = ResourceBundle.getBundle("es.cryptowarts.mensaje", Locale.getDefault());

        cmbOpcion.getItems().addAll(bundle.getString(CIFRAR), bundle.getString(DESCIFRAR));
        cmbOpcion.setValue(bundle.getString(SELECCIONA_OPCION));

        btnAreas.setDisable(true);
        btnLimpiarAreas.setDisable(true);
        btnSelecFichero.setDisable(true);
        lblMensaje.setText("");
        lblMensaje.setVisible(false);
    }

    @FXML
    void areaEscribir() {
        btnAreas.setDisable(txtIzda.getText().trim().isEmpty());
        btnLimpiarAreas.setDisable(txtIzda.getText().trim().isEmpty());
    }

    @FXML
    void btnFichero() {
        lblMensaje.setVisible(false);
        lblArchivo.setText("");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(bundle.getString("seleccionaArchivo"));
        File file = fileChooser.showOpenDialog(null);

        if (file == null) {
            return;
        }

        String clave = pedirClave(bundle.getString("introduceClave"));
        if (clave == null || clave.trim().isEmpty()) {
            mandarAlertas(Alert.AlertType.WARNING, bundle.getString("claveInvalida"), null, bundle.getString("claveInvalidaMensaje"));
            return;
        }

        String opcion = cmbOpcion.getValue();
        if (opcion == null || opcion.equals(bundle.getString(SELECCIONA_OPCION))) {
            mandarAlertas(Alert.AlertType.WARNING, bundle.getString("opcionInvalida"), null, bundle.getString("opcionInvalidaMensaje"));
            return;
        }

        try {
            String resultado;
            if (opcion.equalsIgnoreCase(bundle.getString(CIFRAR))) {
                resultado = CifradoAES.cifrarArchivo(file.getAbsolutePath(), clave);
            } else {
                resultado = CifradoAES.descifrarArchivo(file.getAbsolutePath(), clave);
            }

            lblArchivo.setText(resultado);
            lblMensaje.setText(bundle.getString("archivoProcesado"));
            lblMensaje.setVisible(true);
            logger.info("Archivo procesado: {}", resultado);

        } catch (Exception e) {
            logger.error("Error procesando archivo {}", e.getMessage(), e);
            mandarAlertas(Alert.AlertType.ERROR, bundle.getString("error"), null, bundle.getString("noSePudoProcesarMensaje") + " " + e.getMessage());
        }
    }

    public void btnAccion() {
        String texto = txtIzda.getText();
        if (texto == null || texto.isEmpty()) {
            mandarAlertas(Alert.AlertType.WARNING, bundle.getString("atencion"), bundle.getString("noHayTextoTitulo"), bundle.getString("noHayTextoMensaje"));
            return;
        }

        String opcion = cmbOpcion.getValue();
        if (opcion == null || (!opcion.equals(bundle.getString(CIFRAR)) && !opcion.equals(bundle.getString(DESCIFRAR)))) {
            mandarAlertas(Alert.AlertType.WARNING, bundle.getString("atencion"), bundle.getString("opcionInvalida"), bundle.getString("opcionInvalidaMensaje2"));
            return;
        }

        String clave = pedirClave(bundle.getString("introduceClave"));
        if (clave == null || clave.trim().isEmpty()) {
            mandarAlertas(Alert.AlertType.WARNING, bundle.getString("atencion"), bundle.getString("opcionInvalida"), bundle.getString("claveInvalidaMensaje"));
            return;
        }

        try {
            String resultado;
            if (opcion.equals(bundle.getString(CIFRAR))) {
                resultado = CifradoAES.cifrarTexto(texto, clave);
            } else {
                resultado = CifradoAES.descifrarTexto(texto, clave);
            }

            txtDcha.setText(resultado);

        } catch (Exception e) {
            logger.error("Error al procesar el texto", e);
            mandarAlertas(Alert.AlertType.ERROR, bundle.getString("error"), bundle.getString("noSePuedeProcesarTexto") + " ", e.getMessage());
        }
    }

    @FXML
    void btnAcercaDe() {
        mandarAlertas(Alert.AlertType.INFORMATION, bundle.getString("acercaDe"), null, bundle.getString("acercaDeMensaje"));
    }

    @FXML
    void btnLimpiar() {
        boolean confirmar = mandarConfirmacion(bundle.getString("btnLimpiarAreas"), bundle.getString("limpiarAreasMensaje"));
        if (confirmar) {
            txtDcha.clear();
            txtIzda.clear();
            btnAreas.setDisable(true);
            lblMensaje.setVisible(false);
            btnLimpiarAreas.setDisable(true);
        }
    }

    @FXML
    void btnCerrar() {
        boolean confirmar = mandarConfirmacion(bundle.getString("cerrarAplicacion"), bundle.getString("cerrarAplicacionMensaje"));
        if (confirmar) {
            Platform.exit();
        }
    }

    @FXML
    public void cmbAccion() {
        String opcion = cmbOpcion.getValue();

        btnSelecFichero.setText(bundle.getString("seleccionaArchivo"));

        if (opcion == null) {
            btnAreas.setDisable(true);
            btnSelecFichero.setDisable(true);
            return;
        }

        btnSelecFichero.setDisable(false);

        if (opcion.equals(bundle.getString(CIFRAR))) {
            btnAreas.setText(bundle.getString(CIFRAR));
        } else if (opcion.equals(bundle.getString(DESCIFRAR))) {
            btnAreas.setText(bundle.getString(DESCIFRAR));
        } else {
            btnAreas.setText(bundle.getString("eligeOpcion"));
            btnAreas.setDisable(true);
            btnSelecFichero.setDisable(true);
        }

    }

    /**
     * Muestra una alerta JavaFX con los datos proporcionados.
     *
     * @param tipo          Tipo de alerta (INFO, WARNING, ERROR...)
     * @param titulo        Título de la ventana de alerta
     * @param mensajeTitulo Texto del encabezado de la alerta
     * @param mensaje       Texto del contenido de la alerta
     */
    private void mandarAlertas(Alert.AlertType tipo, String titulo, String mensajeTitulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensajeTitulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
        logger.debug("Alerta mostrada: tipo={}, mensaje={}", tipo, mensaje);
    }

    /**
     * Muestra una alerta de confirmación y espera la respuesta del usuario.
     *
     * @param titulo  Título de la ventana de alerta
     * @param mensaje Texto del contenido de la alerta
     * @return {@code true} si el usuario confirma la acción, {@code false} en caso contrario
     */
    private boolean mandarConfirmacion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private String pedirClave(String mensaje) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(bundle.getString("claveRequerida"));
        dialog.setHeaderText(null);
        dialog.setContentText(mensaje);
        Optional<String> resultado = dialog.showAndWait();
        return resultado.orElse(null);
    }
}
