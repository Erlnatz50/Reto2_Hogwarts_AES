package es.cryptowarts.cifrado;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.nio.file.Files;
import java.io.File;

/**
 * Clase para cifrar y descifrar texto y archivos usando AES en modo CBC con PKCS5Padding.
 */
public class Cifrado {

    /** Algoritmo de cifrado utilizado */
    private static final String ALGORITMO = "AES/CBC/PKCS5Padding";

    /** Vector de inicialización fijo (debe ser único y seguro en producción) */
    private static final String IV = "abcdefghijklmnop";  // 16 bytes

    /**
     * Cifra un texto plano utilizando AES CBC con PKCS5Padding.
     * Devuelve el texto cifrado codificado en Base64 o un mensaje de error detallado.
     *
     * @param textoPlano Texto original sin cifrar
     * @param clave Clave secreta para cifrar (se normaliza a 16 bytes)
     * @return Texto cifrado en Base64 o mensaje de error
     */
    public static String cifrarTexto(String textoPlano, String clave) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec keySpec = new SecretKeySpec(normalizarClave(clave), "AES");

            Cipher cifrado = Cipher.getInstance(ALGORITMO);
            cifrado.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            byte[] encrypted = cifrado.doFinal(textoPlano.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            return "Error al cifrar texto: " + e.getMessage();
        }
    }

    /**
     * Descifra un texto cifrado codificado en Base64 usando AES CBC con PKCS5Padding.
     * Devuelve el texto original o un mensaje de error detallado.
     *
     * @param textoCifrado Texto cifrado en Base64
     * @param clave Clave secreta para descifrar (debe coincidir con la usada para cifrar)
     * @return Texto original descifrado o mensaje de error
     */
    public static String descifrarTexto(String textoCifrado, String clave) throws Exception {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec keySpec = new SecretKeySpec(normalizarClave(clave), "AES");

            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(textoCifrado);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Error al descifrar texto: " + e.getMessage();
        }
    }

    /**
     * Cifra un archivo completo y guarda el archivo cifrado con sufijo "_cifrado" en el mismo directorio.
     * Devuelve la ruta del archivo cifrado o un mensaje de error.
     *
     * @param rutaArchivo Ruta del archivo original a cifrar
     * @param clave Clave secreta de cifrado
     * @return Ruta del archivo cifrado o mensaje de error
     */
    public static String cifrarArchivo(String rutaArchivo, String clave) {
        try {
            File archivo = new File(rutaArchivo);
            byte[] datosArchivo = Files.readAllBytes(archivo.toPath());

            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec keySpec = new SecretKeySpec(normalizarClave(clave), "AES");

            Cipher cifrado = Cipher.getInstance(ALGORITMO);
            cifrado.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encryptedBytes = cifrado.doFinal(datosArchivo);

            String nuevoNombre = crearNombreArchivo(archivo.getName(), "cifrado");
            File nuevoArchivo = new File(archivo.getParent(), nuevoNombre);
            Files.write(nuevoArchivo.toPath(), encryptedBytes);

            return nuevoArchivo.getAbsolutePath();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Descifra un archivo completo y guarda el archivo con sufijo "_descifrado" en el mismo directorio.
     * Devuelve la ruta del archivo descifrado o mensaje de error.
     *
     * @param rutaArchivo Ruta del archivo cifrado a descifrar
     * @param clave Clave secreta usada para descifrar
     * @return Ruta del archivo descifrado o mensaje de error
     */
    public static String descifrarArchivo(String rutaArchivo, String clave) {
        try {
            File archivo = new File(rutaArchivo);
            byte[] datosArchivo = Files.readAllBytes(archivo.toPath());

            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec keySpec = new SecretKeySpec(normalizarClave(clave), "AES");

            Cipher cifrado = Cipher.getInstance(ALGORITMO);
            cifrado.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decryptedBytes = cifrado.doFinal(datosArchivo);

            String nuevoNombre = crearNombreArchivo(archivo.getName(), "descifrado");
            File nuevoArchivo = new File(archivo.getParent(), nuevoNombre);
            Files.write(nuevoArchivo.toPath(), decryptedBytes);

            return nuevoArchivo.getAbsolutePath();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Normaliza la clave para que tenga exactamente 16 bytes (128 bits) para AES.
     * Si es más corta, se rellena con ceros; si es más larga, se trunca.
     *
     * @param clave Clave original en forma de String
     * @return Array de bytes con la clave normalizada a 16 bytes
     */
    private static byte[] normalizarClave(String clave)  {
        byte[] keyBytes = clave.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes16 = new byte[16];
        System.arraycopy(keyBytes, 0, keyBytes16, 0, Math.min(keyBytes.length, 16));
        return keyBytes16;
    }

    /**
     * Construye un nuevo nombre para un archivo añadiendo un sufijo antes de la extensión.
     *
     * @param nombreOriginal Nombre original del archivo (ejemplo: documento.txt)
     * @param operacion Texto a añadir como sufijo (ejemplo: cifrado, descifrado)
     * @return Nombre del archivo modificado (ejemplo: documento_cifrado.txt)
     */
    private static String crearNombreArchivo(String nombreOriginal, String operacion) {
        int indicePunto = nombreOriginal.lastIndexOf('.');
        String nombreBase;
        String extension;

        nombreBase = nombreOriginal.substring(0, indicePunto);
        extension = nombreOriginal.substring(indicePunto);

        return nombreBase + "_" + operacion + extension;
    }
}