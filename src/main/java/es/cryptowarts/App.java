package es.cryptowarts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase principal de la aplicación JavaFX.
 * Esta clase extiende {@link Application} y se encarga de:
 * - Cargar la interfaz desde un archivo FXML.
 * - Aplicar la hoja de estilos CSS.
 * - Configurar el stage principal y mostrar la ventana.
 * - Registrar mensajes de log con SLF4J.
 * Contiene también el metodo {@link #main(String[])} para lanzar la aplicación.
 *
 * @author Erlantz
 * @version 1.0
 */
public class App extends Application {

    /** Logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    /** Bundle del sistema de internacionalización */
    private ResourceBundle bundle;

    /**
     * Metodo principal que se ejecuta al iniciar la aplicación JavaFX.
     * Carga el FXML, aplica el CSS, configura el stage y muestra la ventana.
     * Si ocurre algún error, muestra una alerta y registra el error en el Log.
     *
     * @param stage Stage principal proporcionado por JavaFX.
     */
    @Override
    public void start(Stage stage) {
        try{
            // Detectar el idioma del sistema
            //Locale locale = Locale.getDefault();
            Locale locale = new Locale("es");

            // Bundle del sistema de internacionalización
            bundle = ResourceBundle.getBundle("es.cryptowarts.mensajes", locale);

            logger.debug("Cargando el archivo FXML: ventana.fxml");
            FXMLLoader loaded = new FXMLLoader(getClass().getResource("/es/cryptowarts/fxml/ventana.fxml"), bundle);

            Scene scene = new Scene(loaded.load());
            logger.info("FXML cargado correctamente");

            // Comprobar que el archivo de CSS existe y si no mostrar una alerta
            var archivoCSS = getClass().getResource("/es/cryptowarts/css/estilo.css");
            if(archivoCSS != null){
                logger.info("CSS cargado correctamente");
                scene.getStylesheets().add(archivoCSS.toExternalForm());
            } else{
                logger.error("No se ha podido cargar el CSS");
                mandarAlertas(Alert.AlertType.INFORMATION, bundle.getString("cssNoEncontrado"), null, bundle.getString("cssNoEncontradoMensaje"));
            }

            stage.setTitle("Cifra o descifra con el algortimo de AES");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMinWidth(400);
            stage.setMinHeight(380);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.show();

        } catch (Exception e) {
            logger.error("Error al intentar cargar la aplicación: {}", e.getMessage());
            mandarAlertas(Alert.AlertType.ERROR, bundle.getString("error"), bundle.getString("errorIniciarAplicacion"), bundle.getString("errorIniciarAplicacionMensaje"));
        }
    }

    /**
     * Muestra una alerta JavaFX con los datos proporcionados.
     *
     * @param tipo Tipo de alerta (INFO, WARNING, ERROR...)
     * @param titulo Título de la alerta
     * @param mensajeTitulo Encabezado del mensaje
     * @param mensaje Contenido del mensaje
     *
     * @author Erlantz
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
     * Metodo que se ejecuta cuando cierra la aplicación.
     * Registra un mensaje de cierre en el archivo de Log.
     */
    @Override
    public void stop(){
        logger.info("Aplicación finalizada correctamente");
    }

    /**
     * Metodo principal que lanza la aplicación JavaFX
     *
     * @param args Argumentos de línea de comandos (no usados).
     */
    public static void main(String[] args) {
        logger.info("Iniciando aplicación Cifrar y Descifrar en AES...");
        launch();
    }

}