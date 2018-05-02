package modele;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "andrana", schema = "public", catalog = "hopital")
public class AndranaEntity {
    private int id;
    private String nom;
    private String prenom;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nom", nullable = true, length = 50)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "prenom", nullable = true, length = 50)
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        AndranaEntity that = (AndranaEntity) o;
        return id == that.id &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(prenom, that.prenom);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nom, prenom);
    }
}
