package ec.edu.sistemalicencias.dao;

import ec.edu.sistemalicencias.config.DatabaseConfig;
import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final DatabaseConfig dbConfig = DatabaseConfig.getInstance();

    // 1. LOGIN: Busca un usuario por credenciales
    public Usuario login(String user, String passPlana) throws BaseDatosException {
        String sql = "SELECT * FROM usuarios WHERE username = ?";

        try (Connection con = dbConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuarioEncontrado = mapearUsuario(rs);


                    if (BCrypt.checkpw(passPlana, usuarioEncontrado.getPassword())) {
                        return usuarioEncontrado;
                    }
                }
            }
        } catch (SQLException e) {
            throw new BaseDatosException("Error en base de datos: " + e.getMessage());
        }
        return null;
    }
    // 2. LISTAR: Para que el Admin vea a todos en una tabla
    public List<Usuario> listarTodos() throws BaseDatosException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY id ASC";
        try (Connection con = dbConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            throw new BaseDatosException("Error al listar usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    // 3. GUARDAR (INSERT/UPDATE): El CRUD para el administrador
    public void guardar(Usuario u) throws BaseDatosException {
        String sql;
        boolean esNuevo = (u.getId() == null || u.getId() == 0);

        // CAMBIO: Usamos crypt(?, gen_salt('bf')) para que SQL encripte la clave al guardar
        if (esNuevo) {
            sql = "INSERT INTO usuarios (email, nombre_completo, username, password, rol, activo) " +
                    "VALUES (?, ?, ?, crypt(?, gen_salt('bf')), ?, ?)";
        } else {
            sql = "UPDATE usuarios SET email=?, nombre_completo=?, username=?, " +
                    "password=crypt(?, gen_salt('bf')), rol=?, activo=? WHERE id=?";
        }

        try (Connection con = dbConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getEmail());
            ps.setString(2, u.getNombreCompleto());
            ps.setString(3, u.getUsername());
            ps.setString(4, u.getPassword()); // Pasamos la clave plana, SQL la encriptará
            ps.setString(5, u.getRol());
            ps.setBoolean(6, u.isActivo());

            if (!esNuevo) {
                ps.setLong(7, u.getId());
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDatosException("Error al guardar usuario: " + e.getMessage());
        }
    }

    // 4. ELIMINAR
    public void eliminar(Long id) throws BaseDatosException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection con = dbConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BaseDatosException("Error al eliminar usuario: " + e.getMessage());
        }
    }

    // Método privado para convertir Filas de BD a Objetos Java
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setNombreCompleto(rs.getString("nombre_completo"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password")); // Aquí traemos el hash
        u.setRol(rs.getString("rol"));
        u.setActivo(rs.getBoolean("activo"));
        u.setEmail(rs.getString("email"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            u.setCreatedAt(ts.toLocalDateTime());
        }
        return u;
    }

    // Método para buscar un usuario por ID
    public Usuario buscarPorId(Long id) throws BaseDatosException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection con = dbConfig.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            throw new BaseDatosException("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }
}