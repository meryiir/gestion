package ma.projet.beans;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Femme")
@NamedNativeQuery(
    name = "Femme.nombreEnfantsEntreDates",
    query = "SELECT COALESCE(SUM(m.nbr_enfant), 0) FROM mariage m WHERE m.femme_id = :femmeId AND m.date_debut >= :dateDebut AND m.date_debut <= :dateFin",
    resultClass = Long.class
)
@NamedQuery(
    name = "Femme.femmesMarieesAuMoinsDeuxFois",
    query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2"
)
public class Femme extends Personne {
    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL)
    private List<Mariage> mariages = new ArrayList<>();
    
    public Femme() {
        super();
    }
    
    public Femme(String nom, String prenom, String telephone, String adresse, java.time.LocalDate dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
    
    public List<Mariage> getMariages() {
        return mariages;
    }
    
    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
    
    public void addMariage(Mariage mariage) {
        mariages.add(mariage);
        mariage.setFemme(this);
    }
}

