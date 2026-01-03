package ec.edu.sistemalicencias.service;

import ec.edu.sistemalicencias.dao.UsuarioDAO;
import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario login(String user, String pass) throws Exception {
        try {
            Usuario u = usuarioDAO.login(user, pass);

            if (u == null) {
                throw new Exception("Usuario o contraseña incorrectos");
            }

            return u;

        } catch (BaseDatosException e) {
            throw new Exception("Error al iniciar sesión", e);
        }
    }
}
