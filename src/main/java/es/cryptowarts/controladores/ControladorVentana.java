package es.cryptowarts.controladores;

import es.cryptowarts.cifrado.CifradoAES;
import es.cryptowarts.cifrado.CifradoVigenere;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador de la ventana principal de la aplicación de cifrado.
 * Gestiona la interacción con la interfaz JavaFX y la lógica de cifrado y descifrado
 * según la selección del usuario (Vigenere o AES).
 */
public class ControladorVentana {

    /**
     * Logger para esta clase.
     */
    private static final Logger logger = LoggerFactory.getLogger(ControladorVentana.class);

    /**
     * Bundle para internacionalización de mensajes.
     */
    private ResourceBundle bundle;

    /**
     * Etiqueta para mostrar la ruta del archivo procesado.
     */
    @FXML
    private Label lblArchivo;

    /**
     * Etiqueta para mostrar mensajes informativos o de error.
     */
    @FXML
    private Label lblMensaje;

    /**
     * Área de texto de salida, donde se coloca el texto cifrado o descifrado.
     */
    @FXML
    private TextArea txtDcha;

    /**
     * Área de texto de entrada para el texto a procesar.
     */
    @FXML
    private TextArea txtIzda;

    /**
     * ComboBox para seleccionar la acción: cifrar o descifrar.
     */
    @FXML
    private ComboBox<String> cmbOpcion;

    /**
     * Botón para realizar la acción de cifrar/descifrar en textos.
     */
    @FXML
    private Button btnAreas;

    /**
     * Botón para limpiar áreas de texto.
     */
    @FXML
    private Button btnLimpiarAreas;

    /**
     * Botón para seleccionar archivo.
     */
    @FXML
    private Button btnSelecFichero;

    /**
     * RadioButton para seleccionar cifrado Vigenere.
     */
    @FXML
    private RadioButton rbVigenere;

    /**
     * RadioButton para seleccionar cifrado AES.
     */
    @FXML
    private RadioButton rbAES;

    /**
     * Grupo de toggle para radio buttons de selección de cifrado.
     */
    @FXML
    private ToggleGroup grupoCifrado;

    /**
     * Constante para texto de opción de cifrar.
     */
    private static final String CIFRAR = "cifrar";

    /**
     * Constante para texto de opción de descifrar.
     */
    private static final String DESCIFRAR = "descifrar";

    /**
     * Constante para texto de opción "selecciona opción".
     */
    private static final String SELECCIONA_OPCION = "seleccionaOpcion";

    /**
     * Inicializa el controlador tras cargarse la interfaz.
     * Configura combo box, estado inicial de botones y toggle group.
     */
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

        grupoCifrado = new ToggleGroup();
        rbVigenere.setToggleGroup(grupoCifrado);
        rbAES.setToggleGroup(grupoCifrado);

        rbVigenere.setSelected(true); // opción por defecto
    }

    /**
     * Método llamado al escribir en el área de texto de entrada.
     * Activa o desactiva botones según si el área está vacía o no.
     */
    @FXML
    void areaEscribir() {
        boolean vacio = txtIzda.getText().trim().isEmpty();
        btnAreas.setDisable(vacio);
        btnLimpiarAreas.setDisable(vacio);
    }

    /**
     * Acción asociada al botón para seleccionar un archivo y procesarlo (cifrar/descifrar).
     * Solicita clave y realiza operación según selección del radio button y combo.
     */
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
            mandarAlertas(Alert.AlertType.WARNING, bundle.getString("claveInvalida"), null,
                    bundle.getString("claveInvalidaMensaje"));
            return;
        }

        String opcion = cmbOpcion.getValue();
        try {
            String resultado;
            if (rbVigenere.isSelected()) {
                if (opcion.equalsIgnoreCase(bundle.getString(CIFRAR))) {
                    resultado = CifradoVigenere.cifrarArchivo(file.getAbsolutePath(), clave);
                } else {
                    resultado = CifradoVigenere.descifrarArchivo(file.getAbsolutePath(), clave);
                }
            } else {
                if (opcion.equalsIgnoreCase(bundle.getString(CIFRAR))) {
                    resultado = CifradoAES.cifrarArchivo(file.getAbsolutePath(), clave);
                } else {
                    resultado = CifradoAES.descifrarArchivo(file.getAbsolutePath(), clave);
                }
            }

            lblArchivo.setText(resultado);
            lblMensaje.setText(bundle.getString("archivoProcesado"));
            lblMensaje.setVisible(true);
            logger.info("Archivo procesado: {}", resultado);

        } catch (Exception e) {
            logger.error("Error procesando archivo {}", e.getMessage(), e);
            mandarAlertas(Alert.AlertType.ERROR, bundle.getString("error"), null,
                    bundle.getString("noSePudoProcesarMensaje") + " " + e.getMessage());
        }
    }

    /**
     * Acción asociada al botón para cifrar o descifrar el texto en el área de entrada.
     * Utiliza el radio button para elegir el tipo de cifrado.
     */
    public void btnAccion() {
        String texto = txtIzda.getText();
        if (texto == null || texto.isEmpty()) {
            mandarAlertas(Alert.AlertType.WARNING, bundle.getString("atencion"), bundle.getString("noHayTextoTitulo"),
                    bundle.getString("noHayTextoMensaje"));
            return;
        }

        String clave = pedirClave(bundle.getString("introduceClave"));
        if (clave == null || clave.trim().isEmpty()) {
            mandarAlertas(Alert.AlertType.WARNING, bundle.getString("atencion"), bundle.getString("opcionInvalida"),
                    bundle.getString("claveInvalidaMensaje"));
            return;
        }

        try {
            String resultado;
            if (rbVigenere.isSelected()) {
                if (btnAreas.getText().equalsIgnoreCase(bundle.getString(CIFRAR))) {
                    resultado = CifradoVigenere.cifrarTexto(texto, clave);
                } else {
                    resultado = CifradoVigenere.descifrarTexto(texto, clave);
                }
            } else if (rbAES.isSelected()) {
                if (btnAreas.getText().equalsIgnoreCase(bundle.getString(CIFRAR))) {
                    resultado = CifradoAES.cifrarTexto(texto, clave);
                } else {
                    resultado = CifradoAES.descifrarTexto(texto, clave);
                }
            } else {
                mandarAlertas(Alert.AlertType.WARNING, bundle.getString("atencion"), null,
                        bundle.getString("opcionInvalidaMensaje"));
                return;
            }

            txtDcha.setText(resultado);

        } catch (Exception e) {
            logger.error("Error al procesar el texto", e);
            mandarAlertas(Alert.AlertType.ERROR, bundle.getString("error"), bundle.getString("noSePuedeProcesarTexto") + " ",
                    e.getMessage());
        }
    }

    /**
     * Muestra información "Acerca de" en un cuadro de diálogo.
     */
    @FXML
    void btnAcercaDe() {
        mandarAlertas(Alert.AlertType.INFORMATION, bundle.getString("acercaDe"), null, bundle.getString("acercaDeMensaje"));
    }

    /**
     * Limpia ambas áreas de texto tras confirmación del usuario.
     */
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

    /**
     * Cierra la aplicación tras confirmación del usuario.
     */
    @FXML
    void btnCerrar() {
        boolean confirmar = mandarConfirmacion(bundle.getString("cerrarAplicacion"), bundle.getString("cerrarAplicacionMensaje"));
        if (confirmar) {
            Platform.exit();
        }
    }

    /**
     * Acción para manejar cambio en las opciones del ComboBox.
     * Actualiza texto y estado de botones según la opción seleccionada.
     */
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
     * @param mensajeTitulo Texto del encabezado de la alerta (puede ser {@code null})
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
     * Muestra una alerta de confirmación y retorna la respuesta de usuario.
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

    /**
     * Muestra un cuadro de diálogo para que el usuario introduzca una clave.
     *
     * @param mensaje Mensaje que explica la solicitud de la clave
     * @return Clave introducida o {@code null} si se cancela
     */
    private String pedirClave(String mensaje) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(bundle.getString("claveRequerida"));
        dialog.setHeaderText(null);
        dialog.setContentText(mensaje);
        Optional<String> resultado = dialog.showAndWait();
        return resultado.orElse(null);
    }
}
