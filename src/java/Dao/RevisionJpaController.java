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
import Entidades.Revision;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SERGIO
 */
public class RevisionJpaController implements Serializable {

    public RevisionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Revision revision) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Colmena idColmena = revision.getIdColmena();
            if (idColmena != null) {
                idColmena = em.getReference(idColmena.getClass(), idColmena.getIdColmena());
                revision.setIdColmena(idColmena);
            }
            em.persist(revision);
            if (idColmena != null) {
                idColmena.getRevisionCollection().add(revision);
                idColmena = em.merge(idColmena);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRevision(revision.getIdRevision()) != null) {
                throw new PreexistingEntityException("Revision " + revision + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Revision revision) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Revision persistentRevision = em.find(Revision.class, revision.getIdRevision());
            Colmena idColmenaOld = persistentRevision.getIdColmena();
            Colmena idColmenaNew = revision.getIdColmena();
            if (idColmenaNew != null) {
                idColmenaNew = em.getReference(idColmenaNew.getClass(), idColmenaNew.getIdColmena());
                revision.setIdColmena(idColmenaNew);
            }
            revision = em.merge(revision);
            if (idColmenaOld != null && !idColmenaOld.equals(idColmenaNew)) {
                idColmenaOld.getRevisionCollection().remove(revision);
                idColmenaOld = em.merge(idColmenaOld);
            }
            if (idColmenaNew != null && !idColmenaNew.equals(idColmenaOld)) {
                idColmenaNew.getRevisionCollection().add(revision);
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
                Integer id = revision.getIdRevision();
                if (findRevision(id) == null) {
                    throw new NonexistentEntityException("The revision with id " + id + " no longer exists.");
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
            Revision revision;
            try {
                revision = em.getReference(Revision.class, id);
                revision.getIdRevision();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The revision with id " + id + " no longer exists.", enfe);
            }
            Colmena idColmena = revision.getIdColmena();
            if (idColmena != null) {
                idColmena.getRevisionCollection().remove(revision);
                idColmena = em.merge(idColmena);
            }
            em.remove(revision);
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

    public List<Revision> findRevisionEntities() {
        return findRevisionEntities(true, -1, -1);
    }

    public List<Revision> findRevisionEntities(int maxResults, int firstResult) {
        return findRevisionEntities(false, maxResults, firstResult);
    }

    private List<Revision> findRevisionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Revision.class));
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

    public Revision findRevision(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Revision.class, id);
        } finally {
            em.close();
        }
    }

    public int getRevisionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Revision> rt = cq.from(Revision.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
