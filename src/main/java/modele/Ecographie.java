package modele;

import annotations.Table;
import javax.persistence.*;

@Entity
@Table(name = "ecographie")
@javax.persistence.Table(name = "ecographie")
public class Ecographie extends BaseModele {

    @Column(name = "nom")
    private String nom;

    public Ecographie(Integer id, String table, String nom) throws Exception {
        super(id, table);
        this.nom = nom;
    }

    public Ecographie(String nom) {
        this.nom = nom;
    }

    public Ecographie() {}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
