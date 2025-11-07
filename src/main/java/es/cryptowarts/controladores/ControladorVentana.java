package es.cryptowarts.controladores;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

/**
 *
 */
public class ControladorVentana {

    /** Logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(ControladorVentana.class);

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
    }

    /**  */
    @FXML
    void btnAccion(ActionEvent event) {

    }

    @FXML
    void areaEscribir() {
        btnAreas.setDisable(txtIzda.getText().trim().isEmpty());
    }


    @FXML
    void btnFichero(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona un archivo");
        File file = fileChooser.showOpenDialog(null);  // Puedes pasar la ventana principal aquí

        if (file != null) {
            // Aquí haces lo que necesites con el fichero seleccionado
            System.out.println("Archivo seleccionado: " + file.getAbsolutePath());
        }
    }

    @FXML
    void cmbAccion(ActionEvent event) {
        String opcion = (String) cmbOpcion.getValue();
        if (opcion == null) return;
        switch (opcion) {
            case "Cifrar":
                btnSelecFichero.setText("Selecciona el fichero para cifrar");
                btnAreas.setDisable(false);
                btnSelecFichero.setDisable(false);
                btnAreas.setText("Cifrar");
                break;
            case "Descifrar":
                btnSelecFichero.setText("Selecciona el fichero para descifrar");
                btnSelecFichero.setDisable(false);
                btnAreas.setDisable(false);
                btnAreas.setText("Descifrar");
                break;
            default:
                btnSelecFichero.setText("Selecciona el fichero");
                btnAreas.setText("Elige una opción");
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

}
