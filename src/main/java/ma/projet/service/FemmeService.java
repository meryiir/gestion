package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Service
public class FemmeService implements IDao<Femme> {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public boolean create(Femme o) {
        try {
            entityManager.persist(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean update(Femme o) {
        try {
            entityManager.merge(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean delete(Femme o) {
        try {
            entityManager.remove(entityManager.contains(o) ? o : entityManager.merge(o));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Femme findById(int id) {
        return entityManager.find(Femme.class, id);
    }
    
    @Override
    public List<Femme> findAll() {
        TypedQuery<Femme> query = entityManager.createQuery("SELECT f FROM Femme f", Femme.class);
        return query.getResultList();
    }
    
    /**
     * Exécute une requête native nommée retournant le nombre d'enfants d'une femme entre deux dates
     */
    public long nombreEnfantsEntreDates(int femmeId, LocalDate dateDebut, LocalDate dateFin) {
        jakarta.persistence.Query query = entityManager.createNamedQuery("Femme.nombreEnfantsEntreDates");
        query.setParameter("femmeId", femmeId);
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);
        Object result = query.getSingleResult();
        if (result instanceof Number) {
            return ((Number) result).longValue();
        }
        return 0;
    }
    
    /**
     * Exécute une requête nommée retournant les femmes mariées au moins deux fois
     */
    public List<Femme> femmesMarieesAuMoinsDeuxFois() {
        TypedQuery<Femme> query = entityManager.createNamedQuery(
            "Femme.femmesMarieesAuMoinsDeuxFois",
            Femme.class
        );
        return query.getResultList();
    }
    
    /**
     * Utilise l'API Criteria pour afficher le nombre d'hommes mariés à quatre femmes entre deux dates
     */
    public long nombreHommesMariesAQuatreFemmesEntreDates(LocalDate dateDebut, LocalDate dateFin) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        
        Root<ma.projet.beans.Mariage> mariage = query.from(ma.projet.beans.Mariage.class);
        Join<ma.projet.beans.Mariage, ma.projet.beans.Homme> homme = mariage.join("homme");
        Join<ma.projet.beans.Mariage, ma.projet.beans.Femme> femme = mariage.join("femme");
        
        Predicate dateCondition = cb.and(
            cb.greaterThanOrEqualTo(mariage.get("dateDebut"), dateDebut),
            cb.lessThanOrEqualTo(mariage.get("dateDebut"), dateFin)
        );
        
        query.select(cb.countDistinct(homme))
            .where(dateCondition)
            .groupBy(homme.get("id"))
            .having(cb.equal(cb.countDistinct(femme), 4L));
        
        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        List<Long> results = typedQuery.getResultList();
        return results.size();
    }
    
    /**
     * Retourne la femme la plus âgée
     */
    public Femme getFemmeLaPlusAgee() {
        TypedQuery<Femme> query = entityManager.createQuery(
            "SELECT f FROM Femme f ORDER BY f.dateNaissance ASC",
            Femme.class
        );
        query.setMaxResults(1);
        List<Femme> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}

