package ec.edu.sistemalicencias.model.entities;
import javax.persistence.*;
import java.util.List;

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String nombreRol;
    private String descripcion;

    @OneToMany(mappedBy = "unRol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> listaUsuarios;


    public Rol() {
    }

    public Rol(Long id, String nombreRol, String descripcion, List<Usuario> listaUsuarios) {
        this.id = id;
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
        this.listaUsuarios = listaUsuarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
}
