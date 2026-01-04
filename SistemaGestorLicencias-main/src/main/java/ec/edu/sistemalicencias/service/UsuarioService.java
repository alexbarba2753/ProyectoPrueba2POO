package ec.edu.sistemalicencias.service;

import ec.edu.sistemalicencias.dao.UsuarioDAO;
import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario login(String username, String password) throws Exception {

        try {
            Usuario u = usuarioDAO.login(username, password);

            if (u == null) {
                throw new Exception("Usuario o contraseña incorrectos");
            }

            return u;

        } catch (BaseDatosException e) {
            throw new Exception("Error de conexión con la base de datos", e);
        }
    }
}
