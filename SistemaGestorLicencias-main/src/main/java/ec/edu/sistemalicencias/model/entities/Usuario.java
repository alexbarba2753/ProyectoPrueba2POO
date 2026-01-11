package ec.edu.sistemalicencias.model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Clase Entidad Usuario (POJO)
 * Representa la tabla 'usuarios' de la base de datos.
 */
public class Usuario implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String rol;
    private String nombreCompleto;
    private String email;
    private boolean activo = true;
    private java.time.LocalDateTime createdAt;

    // 1. Constructor vacío
    public Usuario() {
    }

    // 2. Constructor útil para el Login o para crear nuevos usuarios rápido
    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.activo = true;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}