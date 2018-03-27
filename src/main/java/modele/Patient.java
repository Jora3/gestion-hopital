package modele;

import java.sql.Date;

public class Patient extends BaseModele {
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String sexe;

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

    private void setNom(String nom) throws Exception {
        if (nom.isEmpty())
            throw new Exception("Nom de patient vide");
        else
            this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    private void setPrenom(String prenom) throws Exception {
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

    private void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    private void setSexe(String sexe) throws Exception {
        if (sexe.equals("H") || sexe.equals("F"))
            this.sexe = sexe;
        else
            throw new Exception("Sexe de patient invalide : " + sexe);
    }
}
