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
import Entidades.Apiario;
import Entidades.Colmena;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.Revision;
import Entidades.Recoleccion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author SERGIO
 */
public class ColmenaJpaController implements Serializable {

    public ColmenaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Colmena colmena) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (colmena.getApiarioCollection() == null) {
            colmena.setApiarioCollection(new ArrayList<Apiario>());
        }
        if (colmena.getRevisionCollection() == null) {
            colmena.setRevisionCollection(new ArrayList<Revision>());
        }
        if (colmena.getRecoleccionCollection() == null) {
            colmena.setRecoleccionCollection(new ArrayList<Recoleccion>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Apiario> attachedApiarioCollection = new ArrayList<Apiario>();
            for (Apiario apiarioCollectionApiarioToAttach : colmena.getApiarioCollection()) {
                apiarioCollectionApiarioToAttach = em.getReference(apiarioCollectionApiarioToAttach.getClass(), apiarioCollectionApiarioToAttach.getIdApiario());
                attachedApiarioCollection.add(apiarioCollectionApiarioToAttach);
            }
            colmena.setApiarioCollection(attachedApiarioCollection);
            Collection<Revision> attachedRevisionCollection = new ArrayList<Revision>();
            for (Revision revisionCollectionRevisionToAttach : colmena.getRevisionCollection()) {
                revisionCollectionRevisionToAttach = em.getReference(revisionCollectionRevisionToAttach.getClass(), revisionCollectionRevisionToAttach.getIdRevision());
                attachedRevisionCollection.add(revisionCollectionRevisionToAttach);
            }
            colmena.setRevisionCollection(attachedRevisionCollection);
            Collection<Recoleccion> attachedRecoleccionCollection = new ArrayList<Recoleccion>();
            for (Recoleccion recoleccionCollectionRecoleccionToAttach : colmena.getRecoleccionCollection()) {
                recoleccionCollectionRecoleccionToAttach = em.getReference(recoleccionCollectionRecoleccionToAttach.getClass(), recoleccionCollectionRecoleccionToAttach.getIdRecoleccion());
                attachedRecoleccionCollection.add(recoleccionCollectionRecoleccionToAttach);
            }
            colmena.setRecoleccionCollection(attachedRecoleccionCollection);
            em.persist(colmena);
            for (Apiario apiarioCollectionApiario : colmena.getApiarioCollection()) {
                Colmena oldIdColmenaOfApiarioCollectionApiario = apiarioCollectionApiario.getIdColmena();
                apiarioCollectionApiario.setIdColmena(colmena);
                apiarioCollectionApiario = em.merge(apiarioCollectionApiario);
                if (oldIdColmenaOfApiarioCollectionApiario != null) {
                    oldIdColmenaOfApiarioCollectionApiario.getApiarioCollection().remove(apiarioCollectionApiario);
                    oldIdColmenaOfApiarioCollectionApiario = em.merge(oldIdColmenaOfApiarioCollectionApiario);
                }
            }
            for (Revision revisionCollectionRevision : colmena.getRevisionCollection()) {
                Colmena oldIdColmenaOfRevisionCollectionRevision = revisionCollectionRevision.getIdColmena();
                revisionCollectionRevision.setIdColmena(colmena);
                revisionCollectionRevision = em.merge(revisionCollectionRevision);
                if (oldIdColmenaOfRevisionCollectionRevision != null) {
                    oldIdColmenaOfRevisionCollectionRevision.getRevisionCollection().remove(revisionCollectionRevision);
                    oldIdColmenaOfRevisionCollectionRevision = em.merge(oldIdColmenaOfRevisionCollectionRevision);
                }
            }
            for (Recoleccion recoleccionCollectionRecoleccion : colmena.getRecoleccionCollection()) {
                Colmena oldIdColmenaOfRecoleccionCollectionRecoleccion = recoleccionCollectionRecoleccion.getIdColmena();
                recoleccionCollectionRecoleccion.setIdColmena(colmena);
                recoleccionCollectionRecoleccion = em.merge(recoleccionCollectionRecoleccion);
                if (oldIdColmenaOfRecoleccionCollectionRecoleccion != null) {
                    oldIdColmenaOfRecoleccionCollectionRecoleccion.getRecoleccionCollection().remove(recoleccionCollectionRecoleccion);
                    oldIdColmenaOfRecoleccionCollectionRecoleccion = em.merge(oldIdColmenaOfRecoleccionCollectionRecoleccion);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findColmena(colmena.getIdColmena()) != null) {
                throw new PreexistingEntityException("Colmena " + colmena + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Colmena colmena) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Colmena persistentColmena = em.find(Colmena.class, colmena.getIdColmena());
            Collection<Apiario> apiarioCollectionOld = persistentColmena.getApiarioCollection();
            Collection<Apiario> apiarioCollectionNew = colmena.getApiarioCollection();
            Collection<Revision> revisionCollectionOld = persistentColmena.getRevisionCollection();
            Collection<Revision> revisionCollectionNew = colmena.getRevisionCollection();
            Collection<Recoleccion> recoleccionCollectionOld = persistentColmena.getRecoleccionCollection();
            Collection<Recoleccion> recoleccionCollectionNew = colmena.getRecoleccionCollection();
            Collection<Apiario> attachedApiarioCollectionNew = new ArrayList<Apiario>();
            for (Apiario apiarioCollectionNewApiarioToAttach : apiarioCollectionNew) {
                apiarioCollectionNewApiarioToAttach = em.getReference(apiarioCollectionNewApiarioToAttach.getClass(), apiarioCollectionNewApiarioToAttach.getIdApiario());
                attachedApiarioCollectionNew.add(apiarioCollectionNewApiarioToAttach);
            }
            apiarioCollectionNew = attachedApiarioCollectionNew;
            colmena.setApiarioCollection(apiarioCollectionNew);
            Collection<Revision> attachedRevisionCollectionNew = new ArrayList<Revision>();
            for (Revision revisionCollectionNewRevisionToAttach : revisionCollectionNew) {
                revisionCollectionNewRevisionToAttach = em.getReference(revisionCollectionNewRevisionToAttach.getClass(), revisionCollectionNewRevisionToAttach.getIdRevision());
                attachedRevisionCollectionNew.add(revisionCollectionNewRevisionToAttach);
            }
            revisionCollectionNew = attachedRevisionCollectionNew;
            colmena.setRevisionCollection(revisionCollectionNew);
            Collection<Recoleccion> attachedRecoleccionCollectionNew = new ArrayList<Recoleccion>();
            for (Recoleccion recoleccionCollectionNewRecoleccionToAttach : recoleccionCollectionNew) {
                recoleccionCollectionNewRecoleccionToAttach = em.getReference(recoleccionCollectionNewRecoleccionToAttach.getClass(), recoleccionCollectionNewRecoleccionToAttach.getIdRecoleccion());
                attachedRecoleccionCollectionNew.add(recoleccionCollectionNewRecoleccionToAttach);
            }
            recoleccionCollectionNew = attachedRecoleccionCollectionNew;
            colmena.setRecoleccionCollection(recoleccionCollectionNew);
            colmena = em.merge(colmena);
            for (Apiario apiarioCollectionOldApiario : apiarioCollectionOld) {
                if (!apiarioCollectionNew.contains(apiarioCollectionOldApiario)) {
                    apiarioCollectionOldApiario.setIdColmena(null);
                    apiarioCollectionOldApiario = em.merge(apiarioCollectionOldApiario);
                }
            }
            for (Apiario apiarioCollectionNewApiario : apiarioCollectionNew) {
                if (!apiarioCollectionOld.contains(apiarioCollectionNewApiario)) {
                    Colmena oldIdColmenaOfApiarioCollectionNewApiario = apiarioCollectionNewApiario.getIdColmena();
                    apiarioCollectionNewApiario.setIdColmena(colmena);
                    apiarioCollectionNewApiario = em.merge(apiarioCollectionNewApiario);
                    if (oldIdColmenaOfApiarioCollectionNewApiario != null && !oldIdColmenaOfApiarioCollectionNewApiario.equals(colmena)) {
                        oldIdColmenaOfApiarioCollectionNewApiario.getApiarioCollection().remove(apiarioCollectionNewApiario);
                        oldIdColmenaOfApiarioCollectionNewApiario = em.merge(oldIdColmenaOfApiarioCollectionNewApiario);
                    }
                }
            }
            for (Revision revisionCollectionOldRevision : revisionCollectionOld) {
                if (!revisionCollectionNew.contains(revisionCollectionOldRevision)) {
                    revisionCollectionOldRevision.setIdColmena(null);
                    revisionCollectionOldRevision = em.merge(revisionCollectionOldRevision);
                }
            }
            for (Revision revisionCollectionNewRevision : revisionCollectionNew) {
                if (!revisionCollectionOld.contains(revisionCollectionNewRevision)) {
                    Colmena oldIdColmenaOfRevisionCollectionNewRevision = revisionCollectionNewRevision.getIdColmena();
                    revisionCollectionNewRevision.setIdColmena(colmena);
                    revisionCollectionNewRevision = em.merge(revisionCollectionNewRevision);
                    if (oldIdColmenaOfRevisionCollectionNewRevision != null && !oldIdColmenaOfRevisionCollectionNewRevision.equals(colmena)) {
                        oldIdColmenaOfRevisionCollectionNewRevision.getRevisionCollection().remove(revisionCollectionNewRevision);
                        oldIdColmenaOfRevisionCollectionNewRevision = em.merge(oldIdColmenaOfRevisionCollectionNewRevision);
                    }
                }
            }
            for (Recoleccion recoleccionCollectionOldRecoleccion : recoleccionCollectionOld) {
                if (!recoleccionCollectionNew.contains(recoleccionCollectionOldRecoleccion)) {
                    recoleccionCollectionOldRecoleccion.setIdColmena(null);
                    recoleccionCollectionOldRecoleccion = em.merge(recoleccionCollectionOldRecoleccion);
                }
            }
            for (Recoleccion recoleccionCollectionNewRecoleccion : recoleccionCollectionNew) {
                if (!recoleccionCollectionOld.contains(recoleccionCollectionNewRecoleccion)) {
                    Colmena oldIdColmenaOfRecoleccionCollectionNewRecoleccion = recoleccionCollectionNewRecoleccion.getIdColmena();
                    recoleccionCollectionNewRecoleccion.setIdColmena(colmena);
                    recoleccionCollectionNewRecoleccion = em.merge(recoleccionCollectionNewRecoleccion);
                    if (oldIdColmenaOfRecoleccionCollectionNewRecoleccion != null && !oldIdColmenaOfRecoleccionCollectionNewRecoleccion.equals(colmena)) {
                        oldIdColmenaOfRecoleccionCollectionNewRecoleccion.getRecoleccionCollection().remove(recoleccionCollectionNewRecoleccion);
                        oldIdColmenaOfRecoleccionCollectionNewRecoleccion = em.merge(oldIdColmenaOfRecoleccionCollectionNewRecoleccion);
                    }
                }
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
                Integer id = colmena.getIdColmena();
                if (findColmena(id) == null) {
                    throw new NonexistentEntityException("The colmena with id " + id + " no longer exists.");
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
            Colmena colmena;
            try {
                colmena = em.getReference(Colmena.class, id);
                colmena.getIdColmena();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The colmena with id " + id + " no longer exists.", enfe);
            }
            Collection<Apiario> apiarioCollection = colmena.getApiarioCollection();
            for (Apiario apiarioCollectionApiario : apiarioCollection) {
                apiarioCollectionApiario.setIdColmena(null);
                apiarioCollectionApiario = em.merge(apiarioCollectionApiario);
            }
            Collection<Revision> revisionCollection = colmena.getRevisionCollection();
            for (Revision revisionCollectionRevision : revisionCollection) {
                revisionCollectionRevision.setIdColmena(null);
                revisionCollectionRevision = em.merge(revisionCollectionRevision);
            }
            Collection<Recoleccion> recoleccionCollection = colmena.getRecoleccionCollection();
            for (Recoleccion recoleccionCollectionRecoleccion : recoleccionCollection) {
                recoleccionCollectionRecoleccion.setIdColmena(null);
                recoleccionCollectionRecoleccion = em.merge(recoleccionCollectionRecoleccion);
            }
            em.remove(colmena);
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

    public List<Colmena> findColmenaEntities() {
        return findColmenaEntities(true, -1, -1);
    }

    public List<Colmena> findColmenaEntities(int maxResults, int firstResult) {
        return findColmenaEntities(false, maxResults, firstResult);
    }

    private List<Colmena> findColmenaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Colmena.class));
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

    public Colmena findColmena(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Colmena.class, id);
        } finally {
            em.close();
        }
    }

    public int getColmenaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Colmena> rt = cq.from(Colmena.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
