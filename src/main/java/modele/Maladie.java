package modele;

import annotations.Column;
import annotations.Table;

@Table(name = "Maladie")
public class Maladie extends BaseModele {
    @Column(name = "nom")
    private String nom;
    private String description;
    private Integer idDepartement;

    public Maladie(Integer id, String nom, String description, Integer idDepartement) throws Exception {
        super(id);
        this.setNom(nom);
        this.setDescription(description);
        this.setIdDepartement(idDepartement);
    }

    public Maladie(String nom, String description, Integer idDepartement) throws Exception {
        this.setNom(nom);
        this.setDescription(description);
        this.setIdDepartement(idDepartement);
    }

    public Maladie() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws Exception {
        if (nom.isEmpty())
            throw new Exception("Nom de maladie vide");
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws Exception {
        if (description.isEmpty())
            throw new Exception("Description de maladie vide");
        this.description = description;
    }

    public Integer getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(Integer idDepartement) throws Exception {
        if (idDepartement > 0)
            this.idDepartement = idDepartement;
        else
            throw new Exception("Id Departement négatif : " + idDepartement);
    }
}
