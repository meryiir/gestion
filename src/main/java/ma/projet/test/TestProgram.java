package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TestProgram implements CommandLineRunner {
    
    @Autowired
    private HommeService hommeService;
    
    @Autowired
    private FemmeService femmeService;
    
    @Autowired
    private MariageService mariageService;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Début du programme de test ===\n");
        
        // 1. Créer 10 femmes et 5 hommes
        System.out.println("1. Création de 10 femmes et 5 hommes...");
        createTestData();
        System.out.println("✓ Données créées avec succès\n");
        
        // 2. Afficher la liste des femmes
        System.out.println("2. Liste des femmes :");
        afficherListeFemmes();
        System.out.println();
        
        // 3. Afficher la femme la plus âgée
        System.out.println("3. Femme la plus âgée :");
        afficherFemmeLaPlusAgee();
        System.out.println();
        
        // 4. Afficher les épouses d'un homme donné
        System.out.println("4. Épouses d'un homme (entre 1990-01-01 et 2005-12-31) :");
        afficherEpousesHomme();
        System.out.println();
        
        // 5. Afficher le nombre d'enfants d'une femme entre deux dates
        System.out.println("5. Nombre d'enfants d'une femme entre deux dates :");
        afficherNombreEnfantsFemme();
        System.out.println();
        
        // 6. Afficher les femmes mariées deux fois ou plus
        System.out.println("6. Femmes mariées au moins deux fois :");
        afficherFemmesMarieesDeuxFois();
        System.out.println();
        
        // 7. Afficher les hommes mariés à quatre femmes entre deux dates
        System.out.println("7. Nombre d'hommes mariés à quatre femmes entre deux dates :");
        afficherHommesMariesQuatreFemmes();
        System.out.println();
        
        // 8. Afficher les mariages d'un homme avec tous les détails
        System.out.println("8. Mariages d'un homme avec tous les détails :");
        afficherMariagesHommeDetails();
        System.out.println();
        
        System.out.println("=== Fin du programme de test ===");
    }
    
    private void createTestData() {
        // Créer 5 hommes
        Homme h1 = new Homme("SAFI", "SAID", "0612345678", "Casablanca", LocalDate.of(1960, 5, 15));
        Homme h2 = new Homme("ALAMI", "AHMED", "0623456789", "Rabat", LocalDate.of(1965, 8, 20));
        Homme h3 = new Homme("BENALI", "MOHAMED", "0634567890", "Marrakech", LocalDate.of(1970, 3, 10));
        Homme h4 = new Homme("DAOUDI", "HASSAN", "0645678901", "Fès", LocalDate.of(1975, 11, 25));
        Homme h5 = new Homme("EL FILALI", "YOUSSEF", "0656789012", "Tanger", LocalDate.of(1980, 7, 5));
        
        hommeService.create(h1);
        hommeService.create(h2);
        hommeService.create(h3);
        hommeService.create(h4);
        hommeService.create(h5);
        
        // Créer 10 femmes
        Femme f1 = new Femme("RAMI", "SALIMA", "0611111111", "Casablanca", LocalDate.of(1965, 6, 10));
        Femme f2 = new Femme("ALI", "AMAL", "0622222222", "Rabat", LocalDate.of(1970, 9, 15));
        Femme f3 = new Femme("ALAOUI", "WAFA", "0633333333", "Marrakech", LocalDate.of(1975, 12, 20));
        Femme f4 = new Femme("ALAMI", "KARIMA", "0644444444", "Fès", LocalDate.of(1968, 4, 5));
        Femme f5 = new Femme("BENNANI", "FATIMA", "0655555555", "Tanger", LocalDate.of(1972, 8, 30));
        Femme f6 = new Femme("CHAKIR", "NADIA", "0666666666", "Casablanca", LocalDate.of(1978, 2, 14));
        Femme f7 = new Femme("DAHBI", "SANA", "0677777777", "Rabat", LocalDate.of(1980, 10, 22));
        Femme f8 = new Femme("EL FADLI", "LATIFA", "0688888888", "Marrakech", LocalDate.of(1973, 1, 8));
        Femme f9 = new Femme("GHALI", "YASMINE", "0699999999", "Fès", LocalDate.of(1976, 5, 18));
        Femme f10 = new Femme("HAFIDI", "SOUAD", "0610101010", "Tanger", LocalDate.of(1979, 7, 25));
        
        femmeService.create(f1);
        femmeService.create(f2);
        femmeService.create(f3);
        femmeService.create(f4);
        femmeService.create(f5);
        femmeService.create(f6);
        femmeService.create(f7);
        femmeService.create(f8);
        femmeService.create(f9);
        femmeService.create(f10);
        
        // Créer des mariages pour h1 (SAFI SAID) - comme dans l'exemple
        // Mariage échoué
        Mariage m1 = new Mariage(LocalDate.of(1989, 9, 3), LocalDate.of(1990, 9, 3), 0);
        m1.setHomme(h1);
        m1.setFemme(f4); // KARIMA ALAMI
        mariageService.create(m1);
        
        // Mariages en cours
        Mariage m2 = new Mariage(LocalDate.of(1990, 9, 3), null, 4);
        m2.setHomme(h1);
        m2.setFemme(f1); // SALIMA RAMI
        mariageService.create(m2);
        
        Mariage m3 = new Mariage(LocalDate.of(1995, 9, 3), null, 2);
        m3.setHomme(h1);
        m3.setFemme(f2); // AMAL ALI
        mariageService.create(m3);
        
        Mariage m4 = new Mariage(LocalDate.of(2000, 11, 4), null, 3);
        m4.setHomme(h1);
        m4.setFemme(f3); // WAFA ALAOUI
        mariageService.create(m4);
        
        // Créer des mariages pour h2 (ALAMI AHMED) - 4 mariages entre 1990-2005
        Mariage m5 = new Mariage(LocalDate.of(1992, 1, 10), null, 2);
        m5.setHomme(h2);
        m5.setFemme(f5);
        mariageService.create(m5);
        
        Mariage m6 = new Mariage(LocalDate.of(1996, 3, 15), null, 1);
        m6.setHomme(h2);
        m6.setFemme(f6);
        mariageService.create(m6);
        
        Mariage m7 = new Mariage(LocalDate.of(2000, 6, 20), null, 3);
        m7.setHomme(h2);
        m7.setFemme(f7);
        mariageService.create(m7);
        
        Mariage m8 = new Mariage(LocalDate.of(2004, 9, 25), null, 2);
        m8.setHomme(h2);
        m8.setFemme(f8);
        mariageService.create(m8);
        
        // Créer des mariages pour f2 (AMAL ALI) - mariée 2 fois
        Mariage m9 = new Mariage(LocalDate.of(1993, 5, 10), LocalDate.of(1995, 5, 10), 1);
        m9.setHomme(h3);
        m9.setFemme(f2);
        mariageService.create(m9);
        
        // Créer des mariages pour f3 (WAFA ALAOUI) - mariée 2 fois
        Mariage m10 = new Mariage(LocalDate.of(1998, 7, 12), LocalDate.of(2000, 7, 12), 0);
        m10.setHomme(h4);
        m10.setFemme(f3);
        mariageService.create(m10);
    }
    
    private void afficherListeFemmes() {
        List<Femme> femmes = femmeService.findAll();
        for (Femme f : femmes) {
            System.out.println("- " + f.getPrenom() + " " + f.getNom() + 
                    " (Née le: " + f.getDateNaissance() + ")");
        }
    }
    
    private void afficherFemmeLaPlusAgee() {
        Femme femme = femmeService.getFemmeLaPlusAgee();
        if (femme != null) {
            System.out.println("- " + femme.getPrenom() + " " + femme.getNom() + 
                    " (Née le: " + femme.getDateNaissance() + ")");
        } else {
            System.out.println("Aucune femme trouvée");
        }
    }
    
    private void afficherEpousesHomme() {
        // Utiliser h1 (SAFI SAID) - ID devrait être 1
        List<Homme> hommes = hommeService.findAll();
        if (!hommes.isEmpty()) {
            Homme h = hommes.get(0); // Premier homme
            List<Mariage> mariages = hommeService.getEpousesEntreDates(
                h.getId(), 
                LocalDate.of(1990, 1, 1), 
                LocalDate.of(2005, 12, 31)
            );
            System.out.println("Homme: " + h.getPrenom() + " " + h.getNom());
            for (Mariage m : mariages) {
                System.out.println("- " + m.getFemme().getPrenom() + " " + m.getFemme().getNom() + 
                        " (Date début: " + m.getDateDebut() + ")");
            }
        }
    }
    
    private void afficherNombreEnfantsFemme() {
        List<Femme> femmes = femmeService.findAll();
        if (!femmes.isEmpty()) {
            Femme f = femmes.get(0); // Première femme
            long nombreEnfants = femmeService.nombreEnfantsEntreDates(
                f.getId(),
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2005, 12, 31)
            );
            System.out.println("Femme: " + f.getPrenom() + " " + f.getNom());
            System.out.println("Nombre d'enfants entre 1990-01-01 et 2005-12-31: " + nombreEnfants);
        }
    }
    
    private void afficherFemmesMarieesDeuxFois() {
        List<Femme> femmes = femmeService.femmesMarieesAuMoinsDeuxFois();
        for (Femme f : femmes) {
            System.out.println("- " + f.getPrenom() + " " + f.getNom() + 
                    " (" + f.getMariages().size() + " mariage(s))");
        }
        if (femmes.isEmpty()) {
            System.out.println("Aucune femme mariée au moins deux fois");
        }
    }
    
    private void afficherHommesMariesQuatreFemmes() {
        long nombre = femmeService.nombreHommesMariesAQuatreFemmesEntreDates(
            LocalDate.of(1990, 1, 1),
            LocalDate.of(2005, 12, 31)
        );
        System.out.println("Nombre d'hommes mariés à quatre femmes entre 1990-01-01 et 2005-12-31: " + nombre);
    }
    
    private void afficherMariagesHommeDetails() {
        List<Homme> hommes = hommeService.findAll();
        if (!hommes.isEmpty()) {
            // Utiliser le premier homme (SAFI SAID)
            Homme h = hommes.get(0);
            hommeService.afficherMariagesAvecDetails(h.getId());
        }
    }
}

