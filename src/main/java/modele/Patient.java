package modele;

import annotations.Column;
import annotations.Table;

import java.sql.Date;

@Table(name = "Patient")
public class Patient extends BaseModele {
    @Column(name = "nomPatient")
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String sexe;

    public Patient(String table, String nom, String prenom, Date dateNaissance, String sexe) throws Exception {
        super(table);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setDateNaissance(dateNaissance);
        this.setSexe(sexe);
    }

    public Patient(Integer id, String table, String nom, String prenom, Date dateNaissance, String sexe) throws Exception {
        super(id, table);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setDateNaissance(dateNaissance);
        this.setSexe(sexe);
    }

    public Patient(Integer id, String nom, String prenom, String dateNaissance, String sexe) throws Exception {
        super(id);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setDateNaissance(dateNaissance);
        this.setSexe(sexe);
    }

    public Patient(Integer id, String nom, String prenom, Date dateNaissance, String sexe) throws Exception {
        super(id);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setDateNaissance(dateNaissance);
        this.setSexe(sexe);
    }

    public Patient(String nom, String prenom, String dateNaissance, String sexe) throws Exception {
        this.setNom(nom);
        this.setPrenom(prenom);
        setDateNaissance(dateNaissance);
        this.setSexe(sexe);
    }

    public Patient(String nom, String prenom, Date dateNaissance, String sexe) throws Exception {
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setDateNaissance(dateNaissance);
        this.setSexe(sexe);
    }

    public Patient() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws Exception {
        if (nom.isEmpty())
            throw new Exception("Nom de patient vide");
        else
            this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) throws Exception {
        if (prenom.isEmpty())
            throw new Exception("Prenom de patient vide");
        else
            this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    private void setDateNaissance(String dateNaissance) {

    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) throws Exception {
        if (sexe.equals("H") || sexe.equals("F") || sexe.equals("M"))
            this.sexe = sexe;
        else
            throw new Exception("Sexe de patient invalide : " + sexe);
    }
}
