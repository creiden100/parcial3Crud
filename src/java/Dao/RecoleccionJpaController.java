/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Dao.exceptions.NonexistentEntityException;
import Dao.exceptions.PreexistingEntityException;
import Dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Colmena;
import Entidades.Recoleccion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SERGIO
 */
public class RecoleccionJpaController implements Serializable {

    public RecoleccionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recoleccion recoleccion) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Colmena idColmena = recoleccion.getIdColmena();
            if (idColmena != null) {
                idColmena = em.getReference(idColmena.getClass(), idColmena.getIdColmena());
                recoleccion.setIdColmena(idColmena);
            }
            em.persist(recoleccion);
            if (idColmena != null) {
                idColmena.getRecoleccionCollection().add(recoleccion);
                idColmena = em.merge(idColmena);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRecoleccion(recoleccion.getIdRecoleccion()) != null) {
                throw new PreexistingEntityException("Recoleccion " + recoleccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recoleccion recoleccion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Recoleccion persistentRecoleccion = em.find(Recoleccion.class, recoleccion.getIdRecoleccion());
            Colmena idColmenaOld = persistentRecoleccion.getIdColmena();
            Colmena idColmenaNew = recoleccion.getIdColmena();
            if (idColmenaNew != null) {
                idColmenaNew = em.getReference(idColmenaNew.getClass(), idColmenaNew.getIdColmena());
                recoleccion.setIdColmena(idColmenaNew);
            }
            recoleccion = em.merge(recoleccion);
            if (idColmenaOld != null && !idColmenaOld.equals(idColmenaNew)) {
                idColmenaOld.getRecoleccionCollection().remove(recoleccion);
                idColmenaOld = em.merge(idColmenaOld);
            }
            if (idColmenaNew != null && !idColmenaNew.equals(idColmenaOld)) {
                idColmenaNew.getRecoleccionCollection().add(recoleccion);
                idColmenaNew = em.merge(idColmenaNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recoleccion.getIdRecoleccion();
                if (findRecoleccion(id) == null) {
                    throw new NonexistentEntityException("The recoleccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Recoleccion recoleccion;
            try {
                recoleccion = em.getReference(Recoleccion.class, id);
                recoleccion.getIdRecoleccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recoleccion with id " + id + " no longer exists.", enfe);
            }
            Colmena idColmena = recoleccion.getIdColmena();
            if (idColmena != null) {
                idColmena.getRecoleccionCollection().remove(recoleccion);
                idColmena = em.merge(idColmena);
            }
            em.remove(recoleccion);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Recoleccion> findRecoleccionEntities() {
        return findRecoleccionEntities(true, -1, -1);
    }

    public List<Recoleccion> findRecoleccionEntities(int maxResults, int firstResult) {
        return findRecoleccionEntities(false, maxResults, firstResult);
    }

    private List<Recoleccion> findRecoleccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recoleccion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Recoleccion findRecoleccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recoleccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecoleccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recoleccion> rt = cq.from(Recoleccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
