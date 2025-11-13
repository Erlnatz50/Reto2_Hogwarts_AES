
package es.cryptowarts.controladores;

import es.cryptowarts.cifrado.Cifrado;
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

    @FXML
    public void initialize() {
        bundle = ResourceBundle.getBundle("es.cryptowarts.mensaje", Locale.getDefault());

        cmbOpcion.getItems().addAll("Cifrar", "Descifrar");
        cmbOpcion.setValue("Selecciona una opción");

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
        fileChooser.setTitle("Selecciona un archivo");
        File file = fileChooser.showOpenDialog(null);

        if (file == null) {
            return;
        }

        String clave = pedirClave("Introduce la clave para cifrar/descifrar");
        if (clave == null || clave.trim().isEmpty()) {
            mandarAlertas(Alert.AlertType.WARNING, "Clave inválida", "", "Por favor, introduce una clave válida.");
            return;
        }

        String opcion = cmbOpcion.getValue();
        if (opcion == null || opcion.equals("Selecciona una opción")) {
            mandarAlertas(Alert.AlertType.WARNING, "Opción inválida", "", "Seleccione 'Cifrar' o 'Descifrar' antes de continuar.");
            return;
        }

        try {
            String resultado;
            if (opcion.equalsIgnoreCase("Cifrar")) {
                resultado = Cifrado.cifrarArchivo(file.getAbsolutePath(), clave);
            } else {
                resultado = Cifrado.descifrarArchivo(file.getAbsolutePath(), clave);
            }

            lblArchivo.setText(resultado);
            lblMensaje.setText("Archivo procesado:");
            lblMensaje.setVisible(true);
            logger.info("Archivo procesado: {}", resultado);

        } catch (Exception e) {
            logger.error("Error procesando archivo", e);
            mandarAlertas(Alert.AlertType.ERROR, "Error", "", "No se pudo procesar el archivo: " + e.getMessage());
        }
    }

    public void btnAccion() {
        String texto = txtIzda.getText();
        if (texto == null || texto.isEmpty()) {
            mandarAlertas(Alert.AlertType.WARNING, "Atención", "No hay texto para procesar", "Por favor, introduce texto para cifrar o descifrar.");
            return;
        }

        String opcion = cmbOpcion.getValue();
        if (opcion == null || (!opcion.equals("Cifrar") && !opcion.equals("Descifrar"))) {
            mandarAlertas(Alert.AlertType.WARNING, "Atención", "Opción inválida", "Por favor, selecciona 'Cifrar' o 'Descifrar'.");
            return;
        }

        String clave = pedirClave("Introduce la clave para la operación");
        if (clave == null || clave.trim().isEmpty()) {
            mandarAlertas(Alert.AlertType.WARNING, "Atención", "Clave inválida", "Por favor, proporciona una clave válida.");
            return;
        }

        try {
            String resultado;
            if (opcion.equals("Cifrar")) {
                resultado = Cifrado.cifrarTexto(texto, clave);
            } else {
                resultado = Cifrado.descifrarTexto(texto, clave);
            }
            txtDcha.setText(resultado);


        } catch (Exception e) {
            logger.error("Error al procesar el texto", e);
            mandarAlertas(Alert.AlertType.ERROR, "Error", "No se pudo procesar el texto", e.getMessage());
        }
    }

    @FXML
    void btnAcercaDe() {
        mandarAlertas(Alert.AlertType.INFORMATION, "Acerca de", "", "CryptoWarts v1.0\nHecho con JavaFX y AES CBC.");
    }

    @FXML
    void btnLimpiar() {
        boolean confirmar = mandarConfirmacion("Limpiar áreas", "¿Deseas limpiar las áreas?");
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
        boolean confirmar = mandarConfirmacion("Cerrar aplicación", "¿Deseas cerrar la aplicación?");
        if (confirmar) {
            Platform.exit();
        }
    }

    @FXML
    public void cmbAccion() {
        String opcion = cmbOpcion.getValue();

        if (opcion == null) {
            btnAreas.setDisable(true);
            btnSelecFichero.setDisable(true);
            return;
        }


        btnSelecFichero.setDisable(false);

        switch (opcion) {
            case "Cifrar":
                btnSelecFichero.setText("Selecciona un archivo");
                btnAreas.setText("Cifrar");
                break;

            case "Descifrar":
                btnSelecFichero.setText("Selecciona un archivo");
                btnAreas.setText("Descifrar");
                break;

            default:
                btnSelecFichero.setText("Selecciona un archivo");
                btnAreas.setText("Elige una opción");
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
        logger.debug("Alerta mostrada: tipo={}, titulo={}", tipo, titulo);
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
        dialog.setTitle("Clave requerida");
        dialog.setHeaderText(null);
        dialog.setContentText(mensaje);
        Optional<String> resultado = dialog.showAndWait();
        return resultado.orElse(null);
    }
}
