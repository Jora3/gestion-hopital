package modele;

import annotations.NotColumn;
import annotations.Relation;
import annotations.Table;

import java.io.Serializable;
import java.sql.Date;

@Table(name = "Consultation")
public class Consultation extends BaseModele implements Serializable {
    private Date dateConsultation;
    private Integer idMedecin;
    private Integer idPatient;

    @Relation
    @NotColumn
    private transient Medecin medecin;
    @NotColumn
    private transient Patient patient;

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Consultation(Integer id, String table, Date dateConsultation, Integer idMedecin, Integer idPatient) throws Exception {
        super(id, table);
        this.setDateConsultation(dateConsultation);
        this.setIdMedecin(idMedecin);
        this.setIdPatient(idPatient);
    }

    public Consultation(String table, Date dateConsultation, Integer idMedecin, Integer idPatient) throws Exception {
        super(table);
        this.setDateConsultation(dateConsultation);
        this.setIdMedecin(idMedecin);
        this.setIdPatient(idPatient);
    }

    public Consultation(Integer id, Date dateConsultation, Integer idMedecin, Integer idPatient) throws Exception {
        super(id);
        this.setDateConsultation(dateConsultation);
        this.setIdMedecin(idMedecin);
        this.setIdPatient(idPatient);
    }

    public Consultation(Integer id, String table) throws Exception {
        super(id, table);
    }

    public Consultation(String table) throws Exception {
        super(table);
    }

    public Consultation(Integer id) throws Exception {
        super(id);
    }

    public Consultation(Date dateConsultation, Integer idMedecin, Integer idPatient) throws Exception {
        this.setDateConsultation(dateConsultation);
        this.setIdMedecin(idMedecin);
        this.setIdPatient(idPatient);
    }

    public Consultation() {
    }

    public Date getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(Date dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public Integer getIdMedecin() {
        return idMedecin;
    }

    public void setIdMedecin(Integer idMedecin) throws Exception {
        if (idMedecin <= 0)
            throw new Exception("Id medecin négatif ou nul : " + idMedecin);
        this.idMedecin = idMedecin;
    }

    public Integer getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Integer idPatient) throws Exception {
        if (idPatient <= 0)
            throw new Exception("Id patient négatif ou nul : " + idPatient);
        this.idPatient = idPatient;
    }
}
