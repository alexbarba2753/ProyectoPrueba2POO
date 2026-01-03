package ec.edu.sistemalicencias.dao;

import ec.edu.sistemalicencias.config.DatabaseConfig;
import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;
import ec.edu.sistemalicencias.model.interfaces.Persistible;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements Persistible<Usuario> {

    private final DatabaseConfig dbConfig;

    public UsuarioDAO() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    @Override
    public Long guardar(Usuario usuario) throws BaseDatosException {
        // INSERT o UPDATE
        return null;
    }

    @Override
    public Usuario buscarPorId(Long id) throws BaseDatosException {
        return null;
    }

    @Override
    public boolean eliminar(Long id) throws BaseDatosException {
        return false;
    }

    public Usuario login(String username, String password) throws BaseDatosException {
        return null;
    }

    public List<Usuario> listar() throws BaseDatosException {
        return new ArrayList<>();
    }
}

