package ec.edu.sistemalicencias.model.exceptions;

/**
 * Se lanza cuando el usuario o contraseña son incorrectos.
 */
public class AutenticacionException extends Exception {
    public AutenticacionException(String mensaje) {
        super(mensaje);
    }
}