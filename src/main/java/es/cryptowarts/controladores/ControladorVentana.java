package es.cryptowarts.controladores;

import es.cryptowarts.cifrado.Cifrado;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.util.Optional;

/**
 *
 */
public class ControladorVentana {

    /** Logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(ControladorVentana.class);

    @FXML
    private Label lblArchivo;

    @FXML
    private Label lblMensaje;

    /**  */
    @FXML
    private TextArea txtDcha;

    /**  */
    @FXML
    private TextArea txtIzda;

    @FXML
    private ComboBox<String> cmbOpcion;

    @FXML
    private Button btnAreas;

    @FXML
    private Button btnLimpiarAreas;

    @FXML
    private Button btnSelecFichero;

    @FXML
    public void initialize() {
        cmbOpcion.getItems().addAll( "Cifrar", "Descifrar");
        cmbOpcion.setValue("Selecciona una opción");

        txtDcha.setDisable(true);
        btnAreas.setDisable(true);
        btnLimpiarAreas.setDisable(true);
        btnSelecFichero.setDisable(true);
        lblMensaje.setText("");
        lblMensaje.setVisible(false);// Oculta el lblMensaje al inicio
    }


    @FXML
    void areaEscribir() {
        btnAreas.setDisable(txtIzda.getText().trim().isEmpty());
    }


    @FXML
    void btnFichero(ActionEvent event) {
        lblMensaje.setText("");            // Limpia el label de mensajes
        lblArchivo.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona un archivo");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String str = file.getName();
            int indexPunto = str.lastIndexOf('.');

            String extension;

            if (indexPunto > 0) { // hay extensión
                extension = str.substring(indexPunto); // incluye el punto
            } else { // sin extensión
                extension = "";
            }
            // Pedir clave al usuario
            String clave = JOptionPane.showInputDialog(null, "Introduce la clave para cifrar/descifrar", "Clave", JOptionPane.PLAIN_MESSAGE);
            String opcion = (String) cmbOpcion.getValue();


            if (clave != null && !clave.trim().isEmpty()) {
                try {
                    String textoProcesado;
                    if (opcion.equalsIgnoreCase("cifrado") || opcion.equalsIgnoreCase("cifrado")) {
                        // Cifrar el contenido del archivo o texto deseado
                        // Aquí ejemplo cifrando el nombre base (ajustar según necesidad)
                        textoProcesado = Cifrado.cifrarArchivo(file.getName(), clave);
                    } else { // "descifrado"
                        textoProcesado = Cifrado.descifrarArchivo(file.getName(),clave);
                    }


                    logger.info(file.getName());
                    lblArchivo.setText(file.getName());
                    lblMensaje.setText("Archivo creado:");
                    lblMensaje.setVisible(true);
                    logger.info("Archivo procesado: {}", file.getName());

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al procesar con la clave: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    logger.error("Error en cifrado/descifrado", e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Clave no válida o vacía", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void btnAccion(ActionEvent event) {
        String texto = txtIzda.getText();
        if (texto == null || texto.isEmpty()) {
            mandarAlertas(Alert.AlertType.WARNING, "Atención", "No hay texto para procesar", "Por favor escribe texto para cifrar o descifrar.");
            return;
        }

        String opcion = (String) cmbOpcion.getValue();
        if (opcion == null || (!opcion.equals("Cifrar") && !opcion.equals("Descifrar"))) {
            mandarAlertas(Alert.AlertType.WARNING, "Atención", "Opción inválida", "Por favor selecciona 'Cifrar' o 'Descifrar'.");
            return;
        }

        String clave = javax.swing.JOptionPane.showInputDialog(null, "Introduce la clave para la operación", "Clave", javax.swing.JOptionPane.PLAIN_MESSAGE);

        if (clave == null || clave.trim().isEmpty()) {
            mandarAlertas(Alert.AlertType.WARNING, "Atención", "Clave inválida", "Por favor proporciona una clave válida.");
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
            mandarAlertas(Alert.AlertType.ERROR, "Error", "No se pudo procesar el texto", e.getMessage());
        }
    }

    @FXML
    void btnAcercaDe(ActionEvent event) {

    }

    @FXML
    void btnLimpiar(ActionEvent event) {
        txtDcha.clear();
        txtIzda.clear();
    }

    @FXML
    void btnCerrar(ActionEvent event) {
        boolean confirmar = mandarConfirmacion("Cerrar aplicación", "Estas seguro que deseas cerrar la aplicación?", "");

        if (confirmar) {
            Platform.exit();
        }
    }

    /**
     * Muestra una alerta JavaFX con los datos proporcionados.
     *
     * @param tipo Tipo de alerta (INFO, WARNING, ERROR...)
     * @param titulo Título de la ventana de alerta
     * @param mensajeTitulo Texto del encabezado de la alerta
     * @param mensaje Texto del contenido de la alerta
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
     * @param titulo Título de la ventana de alerta
     * @param mensajeTitulo Texto del encabezado de la alerta
     * @param mensaje Texto del contenido de la alerta
     * @return {@code true} si el usuario confirma la acción, {@code false} en caso contrario
     */
    private boolean mandarConfirmacion(String titulo, String mensajeTitulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensajeTitulo);
        alerta.setContentText(mensaje);
        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }


    @FXML
    public void cmbAccion(ActionEvent actionEvent) {
        String opcion = (String) cmbOpcion.getValue();
        if (opcion == null) {
            btnAreas.setDisable(true);
            btnSelecFichero.setDisable(true);
            return;
        }
        btnAreas.setDisable(false);           // activar botón acción
        btnSelecFichero.setDisable(false);    // activar botón selección de archivo

        switch (opcion) {
            case "Cifrar":
                btnSelecFichero.setText("Selecciona el fichero para cifrar");
                btnAreas.setText("Cifrar");
                break;
            case "Descifrar":
                btnSelecFichero.setText("Selecciona el fichero para descifrar");
                btnAreas.setText("Descifrar");
                break;
            default:
                btnSelecFichero.setText("Selecciona el fichero");
                btnAreas.setText("Elige una opción");
                btnAreas.setDisable(true);
                btnSelecFichero.setDisable(true);
        }
    }
}
