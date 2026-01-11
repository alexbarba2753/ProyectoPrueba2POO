package ec.edu.sistemalicencias.service;

import ec.edu.sistemalicencias.dao.UsuarioDAO;
import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.AutenticacionException;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;
import ec.edu.sistemalicencias.model.interfaces.Autenticable;
import java.util.List;

/**
 * Capa de Servicio: Contiene la lógica de negocio para los Usuarios.
 * Implementa Autenticable para cumplir el contrato de seguridad.
 */
public class UsuarioService implements Autenticable {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    @Override
    public Usuario login(String username, String password) throws AutenticacionException {
        if (username.isEmpty() || password.isEmpty()) {
            throw new AutenticacionException("Por favor, complete todos los campos.");
        }

        try {
            Usuario usuario = usuarioDAO.login(username, password);

            // PASO 1: ¿Existe el usuario con esa clave?
            if (usuario == null) {
                throw new AutenticacionException("Usuario o contraseña incorrectos.");
            }

            // PASO 2: Si existe, ¿está activo?
            if (!usuario.isActivo()) {
                throw new AutenticacionException("El acceso está denegado: este usuario se encuentra INACTIVO.");
            }

            return usuario;

        } catch (BaseDatosException e) {
            throw new AutenticacionException("Error técnico: " + e.getMessage());
        }
    }

    // --- MÉTODOS PARA EL GESTOR DE USUARIOS (CRUD) ---

    public List<Usuario> obtenerTodosLosUsuarios() throws BaseDatosException {
        return usuarioDAO.listarTodos();
    }

    public void guardarUsuario(Usuario usuario) throws BaseDatosException {
        // Validámos que el email tenga un formato básico
        if (!usuario.getEmail().contains("@")) {
            throw new BaseDatosException("El formato del correo electrónico es inválido.");
        }
        usuarioDAO.guardar(usuario);
    }

    public void eliminarUsuario(Long id) throws BaseDatosException {
        usuarioDAO.eliminar(id);
    }


    public Usuario obtenerPorId(Long id) throws BaseDatosException {
        // Validamos que el ID sea válido
        if (id == null || id <= 0) {
            throw new BaseDatosException("ID de usuario no válido.");
        }
        return usuarioDAO.buscarPorId(id);
    }
}