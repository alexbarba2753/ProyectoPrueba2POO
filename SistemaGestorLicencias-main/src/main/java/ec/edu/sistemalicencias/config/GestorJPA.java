package ec.edu.sistemalicencias.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GestorJPA {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("LicenciasPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
