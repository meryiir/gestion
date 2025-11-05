package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestGestionProjets {
    
    public static void main(String[] args) {
        try {
            // Initialisation des services
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();
            
            System.out.println("=== Test Application de Gestion de Projets ===\n");
            
            // 1. Création d'employés
            System.out.println("1. Création des employés...");
            Employe emp1 = new Employe("ALAMI", "Ahmed", "0612345678");
            Employe emp2 = new Employe("BENNANI", "Fatima", "0623456789");
            Employe emp3 = new Employe("CHAOUI", "Hassan", "0634567890");
            
            employeService.create(emp1);
            employeService.create(emp2);
            employeService.create(emp3);
            System.out.println("✓ Employés créés avec succès\n");
            
            // 2. Création de projets
            System.out.println("2. Création des projets...");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDebut1 = sdf.parse("2013-01-14");
            Date dateFin1 = sdf.parse("2013-05-30");
            Projet projet1 = new Projet("Gestion de stock", dateDebut1, dateFin1, emp1);
            
            Date dateDebut2 = sdf.parse("2013-02-01");
            Date dateFin2 = sdf.parse("2013-06-15");
            Projet projet2 = new Projet("Application web", dateDebut2, dateFin2, emp2);
            
            projetService.create(projet1);
            projetService.create(projet2);
            System.out.println("✓ Projets créés avec succès\n");
            
            // 3. Création de tâches
            System.out.println("3. Création des tâches...");
            Date tacheDebut1 = sdf.parse("2013-02-10");
            Date tacheFin1 = sdf.parse("2013-02-20");
            Tache tache1 = new Tache("Analyse", tacheDebut1, tacheFin1, 1500.0, projet1);
            
            Date tacheDebut2 = sdf.parse("2013-03-10");
            Date tacheFin2 = sdf.parse("2013-03-15");
            Tache tache2 = new Tache("Conception", tacheDebut2, tacheFin2, 1200.0, projet1);
            
            Date tacheDebut3 = sdf.parse("2013-04-10");
            Date tacheFin3 = sdf.parse("2013-04-25");
            Tache tache3 = new Tache("Développement", tacheDebut3, tacheFin3, 2500.0, projet1);
            
            Date tacheDebut4 = sdf.parse("2013-02-15");
            Date tacheFin4 = sdf.parse("2013-03-01");
            Tache tache4 = new Tache("Tests", tacheDebut4, tacheFin4, 800.0, projet2);
            
            tacheService.create(tache1);
            tacheService.create(tache2);
            tacheService.create(tache3);
            tacheService.create(tache4);
            System.out.println("✓ Tâches créées avec succès\n");
            
            // 4. Création des associations EmployeTache (tâches réalisées)
            System.out.println("4. Création des associations EmployeTache...");
            Date debutReelle1 = sdf.parse("2013-02-10");
            Date finReelle1 = sdf.parse("2013-02-20");
            EmployeTache et1 = new EmployeTache(debutReelle1, finReelle1, emp1, tache1);
            
            Date debutReelle2 = sdf.parse("2013-03-10");
            Date finReelle2 = sdf.parse("2013-03-15");
            EmployeTache et2 = new EmployeTache(debutReelle2, finReelle2, emp2, tache2);
            
            Date debutReelle3 = sdf.parse("2013-04-10");
            Date finReelle3 = sdf.parse("2013-04-25");
            EmployeTache et3 = new EmployeTache(debutReelle3, finReelle3, emp3, tache3);
            
            Date debutReelle4 = sdf.parse("2013-02-15");
            Date finReelle4 = sdf.parse("2013-03-01");
            EmployeTache et4 = new EmployeTache(debutReelle4, finReelle4, emp1, tache4);
            
            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);
            employeTacheService.create(et4);
            System.out.println("✓ Associations créées avec succès\n");
            
            // 5. Tests des méthodes spécifiques
            System.out.println("=== Tests des méthodes spécifiques ===\n");
            
            // Test EmployeService: Afficher les tâches réalisées par un employé
            System.out.println("5. Tâches réalisées par l'employé 1 (ALAMI Ahmed):");
            employeService.afficherTachesRealisees(emp1.getId());
            
            // Test EmployeService: Afficher les projets gérés par un employé
            System.out.println("\n6. Projets gérés par l'employé 1 (ALAMI Ahmed):");
            employeService.afficherProjetsGeres(emp1.getId());
            
            // Test ProjetService: Afficher les tâches planifiées
            System.out.println("\n7. Tâches planifiées pour le projet 1:");
            projetService.afficherTachesPlanifiees(projet1.getId());
            
            // Test ProjetService: Afficher les tâches réalisées avec dates réelles
            System.out.println("\n8. Tâches réalisées pour le projet 1 (avec dates réelles):");
            projetService.afficherTachesRealisees(projet1.getId());
            
            // Test TacheService: Afficher les tâches avec prix > 1000 DH
            System.out.println("\n9. Tâches avec prix supérieur à 1000 DH:");
            tacheService.afficherTachesPrixSup(1000.0);
            
            // Test TacheService: Afficher les tâches réalisées entre deux dates
            System.out.println("\n10. Tâches réalisées entre 2013-02-01 et 2013-03-31:");
            Date dateDebutRecherche = sdf.parse("2013-02-01");
            Date dateFinRecherche = sdf.parse("2013-03-31");
            tacheService.afficherTachesEntreDates(dateDebutRecherche, dateFinRecherche);
            
            System.out.println("\n=== Tests terminés avec succès ===");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fermeture de la SessionFactory
            HibernateUtil.shutdown();
        }
    }
}

