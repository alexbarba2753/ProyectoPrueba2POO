package ec.edu.sistemalicencias.model.interfaces;

import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.AutenticacionException;

public interface Autenticable {
    /**
     * Define el contrato para cualquier sistema de inicio de sesión.
     * @return El objeto Usuario si tiene éxito.
     * @throws AutenticacionException si los datos son inválidos.
     */
    Usuario login(String username, String password) throws AutenticacionException;
}