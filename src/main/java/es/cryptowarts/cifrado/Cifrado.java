package es.cryptowarts.cifrado;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
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
     * Cifra un texto plano usando AES CBC con PKCS5Padding.
     *
     * @param textoPlano (str) Texto a cifrar
     * @param clave      (str) Clave secreta para cifrado (se normaliza a 16 bytes)
     * @return Texto cifrado codificado en Base64
     * @throws Exception Sí ocurre un error durante el cifrado
     */
    public static String cifrarTexto(String textoPlano, String clave) throws Exception {
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
        SecretKeySpec keySpec = new SecretKeySpec(normalizarClave(clave), "AES");

        Cipher cifrado = Cipher.getInstance(ALGORITMO);
        cifrado.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] encrypted = cifrado.doFinal(textoPlano.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Descifra un texto cifrado en Base64 usando AES CBC con PKCS5Padding.
     *
     * @param textoCifrado (str) Texto cifrado en Base64
     * @param clave        (str) Clave secreta para descifrado (debe coincidir con la usada para cifrar)
     * @return Texto descifrado original
     * @throws Exception Sí ocurre un error durante el descifrado
     */
    public static String descifrarTexto(String textoCifrado, String clave) throws Exception {
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
        SecretKeySpec keySpec = new SecretKeySpec(normalizarClave(clave), "AES");

        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(textoCifrado);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
    }

    /**
     * Cifra un archivo completo y genera un nuevo archivo cifrado con sufijo "_cifrado".
     *
     * @param rutaArchivo (str) Ruta del archivo a cifrar
     * @param clave       (str) Clave secreta para cifrado
     * @return Mensaje indicando éxito o error durante la creación del archivo cifrado
     */
    public static String cifrarArchivo(String rutaArchivo, String clave) {
        try {
            File archivo = new File(rutaArchivo);
            byte[] datosArchivo = Files.readAllBytes(archivo.toPath());

            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
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
     * Descifra un archivo completo y genera un nuevo archivo descifrado con sufijo "_descifrado".
     *
     * @param rutaArchivo (str) Ruta del archivo a descifrar
     * @param clave       (str) Clave secreta para descifrado
     * @return Mensaje indicando éxito o error durante la creación del archivo descifrado
     */
    public static String descifrarArchivo(String rutaArchivo, String clave) {
        try {
            File archivo = new File(rutaArchivo);
            byte[] datosArchivo = Files.readAllBytes(archivo.toPath());

            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
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
     * Normaliza una clave para tener 16 bytes (128 bits) para AES.
     * Si la clave es más corta, se rellena con ceros, si es más larga solo se toman los primeros 16 bytes.
     *
     * @param clave Clave original
     * @return Array de bytes con la clave normalizada para AES
     * @throws Exception
     */
    private static byte[] normalizarClave(String clave) throws Exception {
        byte[] keyBytes = clave.getBytes("UTF-8");
        byte[] keyBytes16 = new byte[16];
        System.arraycopy(keyBytes, 0, keyBytes16, 0, Math.min(keyBytes.length, 16));
        return keyBytes16;
    }

    /**
     * Crea un nombre nuevo para archivo que mantiene la extensión original y añade un sufijo.
     *
     * @param nombreOriginal Nombre original del archivo
     * @param operacion      Texto que indica la operación realizada (ej. "cifrado", "descifrado")
     * @return Nombre modificado con sufijo y la misma extensión
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