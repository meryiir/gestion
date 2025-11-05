package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HommeService implements IDao<Homme> {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public boolean create(Homme o) {
        try {
            entityManager.persist(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean update(Homme o) {
        try {
            entityManager.merge(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean delete(Homme o) {
        try {
            entityManager.remove(entityManager.contains(o) ? o : entityManager.merge(o));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Homme findById(int id) {
        return entityManager.find(Homme.class, id);
    }
    
    @Override
    public List<Homme> findAll() {
        TypedQuery<Homme> query = entityManager.createQuery("SELECT h FROM Homme h", Homme.class);
        return query.getResultList();
    }
    
    /**
     * Affiche les épouses d'un homme entre deux dates
     */
    public List<Mariage> getEpousesEntreDates(int hommeId, LocalDate dateDebut, LocalDate dateFin) {
        TypedQuery<Mariage> query = entityManager.createQuery(
            "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId " +
            "AND m.dateDebut >= :dateDebut AND m.dateDebut <= :dateFin",
            Mariage.class
        );
        query.setParameter("hommeId", hommeId);
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);
        return query.getResultList();
    }
    
    /**
     * Affiche les mariages d'un homme avec tous les détails
     */
    public void afficherMariagesAvecDetails(int hommeId) {
        Homme homme = findById(hommeId);
        if (homme == null) {
            System.out.println("Homme non trouvé");
            return;
        }
        
        System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());
        
        // Récupérer tous les mariages
        TypedQuery<Mariage> query = entityManager.createQuery(
            "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId ORDER BY m.dateDebut",
            Mariage.class
        );
        query.setParameter("hommeId", hommeId);
        List<Mariage> mariages = query.getResultList();
        
        // Séparer les mariages en cours et échoués
        List<Mariage> mariagesEnCours = mariages.stream()
            .filter(m -> m.getDateFin() == null)
            .toList();
        
        List<Mariage> mariagesEchoues = mariages.stream()
            .filter(m -> m.getDateFin() != null)
            .toList();
        
        // Afficher les mariages en cours
        System.out.println("Mariages En Cours :");
        int index = 1;
        for (Mariage m : mariagesEnCours) {
            System.out.println(index + ". Femme : " + m.getFemme().getPrenom() + " " + m.getFemme().getNom() +
                    "   Date Début : " + formatDate(m.getDateDebut()) +
                    "    Nbr Enfants : " + m.getNbrEnfant());
            index++;
        }
        
        // Afficher les mariages échoués
        System.out.println("\nMariages échoués :");
        index = 1;
        for (Mariage m : mariagesEchoues) {
            System.out.println(index + ". Femme : " + m.getFemme().getPrenom() + " " + m.getFemme().getNom() +
                    "  Date Début : " + formatDate(m.getDateDebut()) +
                    "    Date Fin : " + formatDate(m.getDateFin()) +
                    "    Nbr Enfants : " + m.getNbrEnfant());
            index++;
        }
    }
    
    private String formatDate(LocalDate date) {
        if (date == null) return "";
        return String.format("%02d/%02d/%d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
    }
}

