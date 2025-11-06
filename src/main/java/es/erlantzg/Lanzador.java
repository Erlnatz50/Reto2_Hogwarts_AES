package es.erlantzg;

/**
 * Clase lanzadora de la aplicación JavaFX.
 * Se utiliza cuando se necesita un punto de entrada separado para empaquetar
 * o ejecutar la aplicación, como: crear un JAR ejecutable.
 *
 * @author Erlantz Garcia
 * @version 1.0
 */
public class Lanzador {

    /**
     * Metodo principal de la clase lanzadora.
     * Recibe los argumentos de línea de comandos y los pasa al
     * {@link es.erlantzg.App#main(String[])} para iniciar la aplicacion.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args){
        App.main(args);
    }
}
