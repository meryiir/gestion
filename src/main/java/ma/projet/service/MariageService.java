package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MariageService implements IDao<Mariage> {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public boolean create(Mariage o) {
        try {
            entityManager.persist(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean update(Mariage o) {
        try {
            entityManager.merge(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean delete(Mariage o) {
        try {
            entityManager.remove(entityManager.contains(o) ? o : entityManager.merge(o));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Mariage findById(int id) {
        return entityManager.find(Mariage.class, id);
    }
    
    @Override
    public List<Mariage> findAll() {
        TypedQuery<Mariage> query = entityManager.createQuery("SELECT m FROM Mariage m", Mariage.class);
        return query.getResultList();
    }
}

