package modele;

import annotations.Column;
import annotations.Table;

@Table(name = "eco_graphie")
public class Ecographie extends BaseModele {

    @Column(name = "type_eco") private String andrana;
    private String type_eco;
    private String nom_eco;
    private String gravite_eco;

    public void setType_eco(String type_eco) {
        this.type_eco = type_eco;
    }

    public void setNom_eco(String nom_eco) {
        this.nom_eco = nom_eco;
    }

    public void setGravite_eco(String gravite_eco) {
        this.gravite_eco = gravite_eco;
    }

    public void setAndrana(String andrana) {
        this.andrana = andrana;
    }

    public String getType_eco() {
        return type_eco;
    }

    public String getNom_eco() {
        return nom_eco;
    }

    public String getGravite_eco() {
        return gravite_eco;
    }

    public String getAndrana() {
        return andrana;
    }
}
