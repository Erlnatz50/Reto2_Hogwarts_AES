package es.cryptowarts.cifrado;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import java.security.GeneralSecurityException;

/**
 * Clase para cifrar y descifrar texto y archivos usando el cifrado clásico Vigenère.
 * Esta versión usa concatenación de String tradicional para construir los textos.
 *
 * @author Telmo
 * @version 1.0
 */
public class CifradoVigenere {

    /**
     * Cifra un texto plano usando el cifrado Vigenère.
     * @param textoPlano Texto original sin cifrar
     * @param clave Clave de cifrado (se repite correspondiente al texto)
     * @return Texto cifrado o mensaje de error
     * @throws GeneralSecurityException si la clave es nula o vacía
     *
     * @author Telmo
     */
    public static String cifrarTexto(String textoPlano, String clave) throws GeneralSecurityException {
        StringBuilder cifrado = new StringBuilder();
        int claveLen = clave.length();
        for (int i = 0; i < textoPlano.length(); i++) {
            char plainChar = textoPlano.charAt(i);
            char keyChar = clave.charAt(i % claveLen);
            if (Character.isLetter(plainChar)) {
                char base = Character.isUpperCase(plainChar) ? 'A' : 'a';
                char baseKey = Character.isUpperCase(keyChar) ? 'A' : 'a';
                int shift = (plainChar - base + (keyChar - baseKey)) % 26;
                cifrado.append((char) (base + shift));
            } else {
                cifrado.append(plainChar);
            }
        }
        return cifrado.toString();
    }

    /**
     * Descifra un texto cifrado con Vigenère.
     * @param textoCifrado Texto cifrado
     * @param clave Clave usada para descifrar (misma clave del cifrado)
     * @return Texto descifrado o mensaje de error
     * @throws GeneralSecurityException si la clave es nula o vacía
     *
     * @author Telmo
     */
    public static String descifrarTexto(String textoCifrado, String clave) throws GeneralSecurityException {
        StringBuilder descifrado = new StringBuilder();
        int claveLen = clave.length();
        for (int i = 0; i < textoCifrado.length(); i++) {
            char encChar = textoCifrado.charAt(i);
            char keyChar = clave.charAt(i % claveLen);
            if (Character.isLetter(encChar)) {
                char base = Character.isUpperCase(encChar) ? 'A' : 'a';
                char baseKey = Character.isUpperCase(keyChar) ? 'A' : 'a';
                int shift = (encChar - base - (keyChar - baseKey) + 26) % 26;
                descifrado.append((char) (base + shift));
            } else {
                descifrado.append(encChar);
            }
        }
        return descifrado.toString();
    }

    /**
     * Cifra un archivo como texto usando Vigenère y guarda el archivo cifrado con sufijo "_cifrado".
     *
     * @param rutaArchivo Ruta del archivo a cifrar
     * @param clave Clave de cifrado
     * @return Ruta del archivo cifrado o mensaje de error
     * @throws IOException si ocurre error de lectura o escritura
     * @throws GeneralSecurityException si la clave es inválida
     *
     * @author Telmo
     */
    public static String cifrarArchivo(String rutaArchivo, String clave) throws IOException, GeneralSecurityException {
        File archivo = new File(rutaArchivo);
        String texto = Files.readString(archivo.toPath());

        String cifrado = cifrarTexto(texto, clave);

        String nuevoNombre = crearNombreArchivo(archivo.getName(), "cifrado");
        File nuevoArchivo = new File(archivo.getParent(), nuevoNombre);
        Files.write(nuevoArchivo.toPath(), cifrado.getBytes(StandardCharsets.UTF_8));

        return nuevoArchivo.getAbsolutePath();
    }

    /**
     * Descifra un archivo Vigenère y guarda el archivo descifrado con sufijo "_descifrado".
     *
     * @param rutaArchivo Ruta del archivo cifrado
     * @param clave Clave de descifrado
     * @return Ruta del archivo descifrado o mensaje de error
     * @throws IOException si ocurre error de lectura o escritura
     * @throws GeneralSecurityException si la clave es inválida
     *
     * @author Telmo
     */
    public static String descifrarArchivo(String rutaArchivo, String clave) throws IOException, GeneralSecurityException {
        File archivo = new File(rutaArchivo);
        String cifrado = Files.readString(archivo.toPath());

        String descifrado = descifrarTexto(cifrado, clave);

        String nuevoNombre = crearNombreArchivo(archivo.getName(), "descifrado");
        File nuevoArchivo = new File(archivo.getParent(), nuevoNombre);
        Files.write(nuevoArchivo.toPath(), descifrado.getBytes(StandardCharsets.UTF_8));

        return nuevoArchivo.getAbsolutePath();
    }

    /**
     * Construye un nuevo nombre para un archivo añadiendo un sufijo antes de la extensión.
     *
     * @param nombreOriginal Nombre original del archivo
     * @param operacion Texto a añadir como sufijo
     * @return Nombre modificado con sufijo
     *
     * @author Telmo
     */
    private static String crearNombreArchivo(String nombreOriginal, String operacion) {
        int indicePunto = nombreOriginal.lastIndexOf('.');
        if (indicePunto == -1) return nombreOriginal + "_" + operacion;
        String nombreBase = nombreOriginal.substring(0, indicePunto);
        String extension = nombreOriginal.substring(indicePunto);
        return nombreBase + "_" + operacion + extension;
    }
}
