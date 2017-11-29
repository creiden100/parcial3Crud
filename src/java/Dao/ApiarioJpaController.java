/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Dao.exceptions.NonexistentEntityException;
import Dao.exceptions.PreexistingEntityException;
import Dao.exceptions.RollbackFailureException;
import Entidades.Apiario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Colmena;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SERGIO
 */
public class ApiarioJpaController implements Serializable {

    public ApiarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Apiario apiario) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Colmena idColmena = apiario.getIdColmena();
            if (idColmena != null) {
                idColmena = em.getReference(idColmena.getClass(), idColmena.getIdColmena());
                apiario.setIdColmena(idColmena);
            }
            em.persist(apiario);
            if (idColmena != null) {
                idColmena.getApiarioCollection().add(apiario);
                idColmena = em.merge(idColmena);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findApiario(apiario.getIdApiario()) != null) {
                throw new PreexistingEntityException("Apiario " + apiario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Apiario apiario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Apiario persistentApiario = em.find(Apiario.class, apiario.getIdApiario());
            Colmena idColmenaOld = persistentApiario.getIdColmena();
            Colmena idColmenaNew = apiario.getIdColmena();
            if (idColmenaNew != null) {
                idColmenaNew = em.getReference(idColmenaNew.getClass(), idColmenaNew.getIdColmena());
                apiario.setIdColmena(idColmenaNew);
            }
            apiario = em.merge(apiario);
            if (idColmenaOld != null && !idColmenaOld.equals(idColmenaNew)) {
                idColmenaOld.getApiarioCollection().remove(apiario);
                idColmenaOld = em.merge(idColmenaOld);
            }
            if (idColmenaNew != null && !idColmenaNew.equals(idColmenaOld)) {
                idColmenaNew.getApiarioCollection().add(apiario);
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
                Integer id = apiario.getIdApiario();
                if (findApiario(id) == null) {
                    throw new NonexistentEntityException("The apiario with id " + id + " no longer exists.");
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
            Apiario apiario;
            try {
                apiario = em.getReference(Apiario.class, id);
                apiario.getIdApiario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The apiario with id " + id + " no longer exists.", enfe);
            }
            Colmena idColmena = apiario.getIdColmena();
            if (idColmena != null) {
                idColmena.getApiarioCollection().remove(apiario);
                idColmena = em.merge(idColmena);
            }
            em.remove(apiario);
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

    public List<Apiario> findApiarioEntities() {
        return findApiarioEntities(true, -1, -1);
    }

    public List<Apiario> findApiarioEntities(int maxResults, int firstResult) {
        return findApiarioEntities(false, maxResults, firstResult);
    }

    private List<Apiario> findApiarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Apiario.class));
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

    public Apiario findApiario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Apiario.class, id);
        } finally {
            em.close();
        }
    }

    public int getApiarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Apiario> rt = cq.from(Apiario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
