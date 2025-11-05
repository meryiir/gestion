package ma.projet.beans;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Homme")
public class Homme extends Personne {
    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL)
    private List<Mariage> mariages = new ArrayList<>();
    
    public Homme() {
        super();
    }
    
    public Homme(String nom, String prenom, String telephone, String adresse, java.time.LocalDate dateNaissance) {
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
        mariage.setHomme(this);
    }
}

