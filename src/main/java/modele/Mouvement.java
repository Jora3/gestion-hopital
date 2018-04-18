/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Date;

/**
 *
 * @author Nathalie Andrandrain
 */
import annotations.Column;
import annotations.Table;

@Table(name = "mouvement")
public class Mouvement extends BaseModele{
private int patient;
private int service;
private int chambre;
private Date dateEntre;
private Date dateSortie;

    public Mouvement() {
    }

    public Mouvement(int patient, int service, int chambre, Date dateEntre, Date dateSortie, Integer id) throws Exception {
        super(id);
        this.patient = patient;
        this.service = service;
        this.chambre = chambre;
        this.dateEntre = dateEntre;
        this.dateSortie = dateSortie;
    }
    
    public Mouvement(int patient, int service, int chambre, Date dateEntre, Date dateSortie) {
        this.patient = patient;
        this.service = service;
        this.chambre = chambre;
        this.dateEntre = dateEntre;
        this.dateSortie = dateSortie;
    }

    public int getPatient() {
        return patient;
    }

    public int getService() {
        return service;
    }

    public int getChambre() {
        return chambre;
    }

    public Date getDateEntre() {
        return dateEntre;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public void setService(int service) {
        this.service = service;
    }

    public void setChambre(int chambre) {
        this.chambre = chambre;
    }

    public void setDateEntre(Date dateEntre) {
        this.dateEntre = dateEntre;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    
}
