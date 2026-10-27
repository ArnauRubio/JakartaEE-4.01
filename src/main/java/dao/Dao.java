package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

abstract class Dao<T, K> {
    EntityManager em = Persistence
            .createEntityManagerFactory("default")
            .createEntityManager();

    public EntityManager getEntityManager() {
        return this.em;
    }

    public abstract T find(K id);

    public T create(T t) {
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        try {
            em.persist(t);
            entityTransaction.commit();
            return t;
        } catch (RuntimeException exception) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw exception;
        }
    }

    public T update(T t) {
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        try {
            T updated = em.merge(t);
            entityTransaction.commit();
            return updated;
        } catch (RuntimeException exception) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw exception;
        }
    }

    public void delete(T t) {
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        try {
            em.remove(t);
            entityTransaction.commit();
        } catch (RuntimeException exception) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw exception;
        }
    }
}