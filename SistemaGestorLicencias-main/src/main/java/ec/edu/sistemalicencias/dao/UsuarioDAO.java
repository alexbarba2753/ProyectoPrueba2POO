package ec.edu.sistemalicencias.dao;

import ec.edu.sistemalicencias.config.GestorJPA;
import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.BaseDatosException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class UsuarioDAO {

    private final EntityManager em = GestorJPA.getEntityManager();

    public Usuario login(String user, String pass) {
        try {
            return em.createQuery("""
                SELECT u FROM Usuario u
                WHERE u.username = :u
                AND u.password = :p
                AND u.activo = true
            """, Usuario.class)
                    .setParameter("u", user)
                    .setParameter("p", pass)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Usuario> listar() {
        return em.createQuery("FROM Usuario", Usuario.class).getResultList();
    }

    public void guardar(Usuario u){
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
    }

    public void eliminar(Long id){
        em.getTransaction().begin();
        em.remove(em.find(Usuario.class,id));
        em.getTransaction().commit();
    }
}

