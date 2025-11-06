package es.erlantzg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase principal de la aplicación JavaFX.
 * Esta clase extiende {@link Application} y se encarga de:
 * - Cargar la interfaz desde un archivo FXML.
 * - Aplicar la hoja de estilos CSS.
 * - Configurar el stage principal y mostrar la ventana.
 * - Registrar mensajes de log con SLF4J.
 * Contiene también el metodo {@link #main(String[])} para lanzar la aplicación.
 *
 * @author Erlantz García
 * @version 1.0
 */
public class App extends Application {

    /** Logger para esta clase */
    private static final Logger logger = LoggerFactory.getLogger(es.erlantzg.App.class);

    /**
     * Metodo principal que se ejecuta al iniciar la aplicación JavaFX.
     * Carga el FXML, aplica el CSS, configura el stage y muetra la ventana.
     * Si ocurre algún error, muestra una alerta y registra el error en el Log.
     *
     * @param stage Stage principal proporcionado por JavaFX.
     */
    @Override
    public void start(Stage stage) {
        try{
            logger.debug("Intentando cargar el FXML: /fxml/visualizarCliente.fxml");
            FXMLLoader loaded = new FXMLLoader(getClass().getResource("/fxml/visualizarCliente.fxml"));
            Scene scene = new Scene(loaded.load());
            logger.info("FXML cargado correctamente");

            // Comprobar que el archivo de CSS existe y si no mostrar una alerta
            var archivoCSS = getClass().getResource("/css/estilo.css");
            if(archivoCSS != null){
                logger.info("CSS cargado correctamente");
                scene.getStylesheets().add(archivoCSS.toExternalForm());
            } else{
                logger.error("No se ha podido cargar el CSS");
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("CSS no encontrado");
                alerta.setHeaderText(null);
                alerta.setContentText("No se ha podido cargar la hoja de estilos CSS");
                alerta.showAndWait();
            }

            stage.setTitle("Adding/Deleting Rows in a TableViews");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMinWidth(400);
            stage.setMinHeight(350);
            stage.setMaxWidth(500);
            stage.setMaxHeight(500);
            stage.show();

        } catch (Exception e) {
            logger.error("Error al intentar cargar la aplicación", e.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al iniciar la aplicación");
            alerta.setContentText("Se ha producido un error al intentar cargar la aplicación");
            alerta.showAndWait();
        }
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
        logger.info("Iniciando aplicación JavaFX...");
        launch();
    }

}