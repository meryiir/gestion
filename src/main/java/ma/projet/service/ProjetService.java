package ma.projet.service;

import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.text.SimpleDateFormat;

public class ProjetService implements IDao<Projet> {
    
    @Override
    public boolean create(Projet o) {
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
    public boolean update(Projet o) {
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
    public boolean delete(Projet o) {
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
    public Projet findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Projet.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Projet> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Projet", Projet.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Afficher la liste des tâches planifiées pour un projet
    public void afficherTachesPlanifiees(int projetId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Projet projet = session.get(Projet.class, projetId);
            if (projet != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
                System.out.println("\nProjet : " + projet.getId() + 
                                 "\tNom : " + projet.getNom() + 
                                 "\tDate début : " + sdf.format(projet.getDateDebut()));
                System.out.println("Liste des tâches planifiées:");
                List<Tache> taches = projet.getTaches();
                if (taches != null && !taches.isEmpty()) {
                    System.out.println("Num\tNom\t\tDate Début\tDate Fin");
                    for (Tache tache : taches) {
                        System.out.println(tache.getId() + "\t" + tache.getNom() + "\t\t" + 
                                         tache.getDateDebut() + "\t" + tache.getDateFin());
                    }
                } else {
                    System.out.println("Aucune tâche planifiée.");
                }
            } else {
                System.out.println("Projet non trouvé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    // Afficher la liste des tâches réalisées avec les dates réelles
    public void afficherTachesRealisees(int projetId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Projet projet = session.get(Projet.class, projetId);
            if (projet != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
                SimpleDateFormat sdfShort = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println("\nProjet : " + projet.getId() + 
                                 "\tNom : " + projet.getNom() + 
                                 "\tDate début : " + sdf.format(projet.getDateDebut()));
                System.out.println("Liste des tâches:");
                
                List<Tache> taches = projet.getTaches();
                if (taches != null && !taches.isEmpty()) {
                    System.out.println("Num Nom            Date Début Réelle   Date Fin Réelle");
                    boolean hasRealisedTasks = false;
                    for (Tache tache : taches) {
                        List<EmployeTache> employeTaches = tache.getEmployeTaches();
                        if (employeTaches != null && !employeTaches.isEmpty()) {
                            for (EmployeTache et : employeTaches) {
                                System.out.println(String.format("%-3d %-15s %-19s %s", 
                                    tache.getId(), 
                                    tache.getNom(), 
                                    sdfShort.format(et.getDateDebutReelle()), 
                                    sdfShort.format(et.getDateFinReelle())));
                                hasRealisedTasks = true;
                            }
                        }
                    }
                    if (!hasRealisedTasks) {
                        System.out.println("Aucune tâche réalisée.");
                    }
                } else {
                    System.out.println("Aucune tâche pour ce projet.");
                }
            } else {
                System.out.println("Projet non trouvé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

