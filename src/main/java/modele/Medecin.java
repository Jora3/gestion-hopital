package modele;

import annotations.Table;

@Table(name = "Medecin")
public class Medecin extends BaseModele {
    private String nom;
    private String prenom;

    public Medecin(String nom, String prenom) throws Exception {
        this.setNom(nom);
        this.setPrenom(prenom);
    }

    public Medecin(Integer id, String nom, String prenom) throws Exception {
        super(id);
        this.setNom(nom);
        this.setPrenom(prenom);
    }

    public Medecin(String table, String nom, String prenom) throws Exception {
        super(table);
        this.setNom(nom);
        this.setPrenom(prenom);
    }

    public Medecin(Integer id, String table, String nom, String prenom) throws Exception {
        super(id, table);
        this.setNom(nom);
        this.setPrenom(prenom);
    }

    public Medecin(Integer id, String table) throws Exception {
        super(id, table);
    }

    public Medecin(String table) throws Exception {
        super(table);
    }

    public Medecin(Integer id) throws Exception {
        super(id);
    }

    public Medecin() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws Exception {
        if (nom.isEmpty())
            throw new Exception("Nom vide");
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) throws Exception {
        if (prenom.isEmpty())
            throw new Exception("Prenom vide");
        this.prenom = prenom;
    }
}
