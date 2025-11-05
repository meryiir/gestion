package ma.projet.service;

import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {
    
    @Override
    public boolean create(Tache o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public boolean update(Tache o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public boolean delete(Tache o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public Tache findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Tache.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Tache> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Tache", Tache.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Afficher les tâches dont le prix est supérieur à 1000 DH (requête nommée)
    public void afficherTachesPrixSup(double prix) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Tache> query = session.createNamedQuery("Tache.findByPrixSup", Tache.class);
            query.setParameter("prix", prix);
            List<Tache> taches = query.list();
            
            System.out.println("\n=== Tâches avec prix supérieur à " + prix + " DH ===");
            if (taches != null && !taches.isEmpty()) {
                System.out.println("Num\tNom\t\t\tPrix");
                for (Tache tache : taches) {
                    System.out.println(tache.getId() + "\t" + 
                                     String.format("%-15s", tache.getNom()) + "\t" + 
                                     tache.getPrix() + " DH");
                }
            } else {
                System.out.println("Aucune tâche trouvée.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    // Afficher les tâches réalisées entre deux dates
    public void afficherTachesEntreDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Tache> query = session.createQuery(
                "SELECT DISTINCT t FROM Tache t " +
                "JOIN t.employeTaches et " +
                "WHERE et.dateDebutReelle BETWEEN :dateDebut AND :dateFin " +
                "OR et.dateFinReelle BETWEEN :dateDebut AND :dateFin",
                Tache.class
            );
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            List<Tache> taches = query.list();
            
            System.out.println("\n=== Tâches réalisées entre " + dateDebut + " et " + dateFin + " ===");
            if (taches != null && !taches.isEmpty()) {
                System.out.println("Num\tNom\t\t\tDate Début Réelle\tDate Fin Réelle");
                for (Tache tache : taches) {
                    List<ma.projet.classes.EmployeTache> employeTaches = tache.getEmployeTaches();
                    if (employeTaches != null) {
                        for (ma.projet.classes.EmployeTache et : employeTaches) {
                            if ((et.getDateDebutReelle().compareTo(dateDebut) >= 0 && 
                                 et.getDateDebutReelle().compareTo(dateFin) <= 0) ||
                                (et.getDateFinReelle().compareTo(dateDebut) >= 0 && 
                                 et.getDateFinReelle().compareTo(dateFin) <= 0)) {
                                System.out.println(tache.getId() + "\t" + 
                                                 String.format("%-15s", tache.getNom()) + "\t" + 
                                                 et.getDateDebutReelle() + "\t\t" + 
                                                 et.getDateFinReelle());
                            }
                        }
                    }
                }
            } else {
                System.out.println("Aucune tâche trouvée.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

