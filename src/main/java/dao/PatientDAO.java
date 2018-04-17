package dao;

import modele.BaseModele;
import modele.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO implements InterfaceDAO {

    @Override
    public List<BaseModele> findAll() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = UtilDAO.getConnection();
            statement = connection.prepareStatement(getRequeteFindAll());
            resultSet = statement.executeQuery();
            return getResultAllPatients(resultSet);
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            UtilDAO.closeRessources(resultSet, statement, connection);
        }
    }

    @Override
    public List<BaseModele> findAll(BaseModele baseModele) throws Exception {
        return null;
    }

    private List<BaseModele> getResultAllPatients(ResultSet resultSet) throws Exception {
        ArrayList<BaseModele> patients = new ArrayList<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String nom = resultSet.getString(2),
                    prenom = resultSet.getString(3),
                    sexe = resultSet.getString(5);
            Date dateNaissance = resultSet.getDate(4);
            patients.add(new Patient(id, nom, prenom, dateNaissance, sexe));
        }
        return patients;
    }

    @Override
    public void findById(BaseModele modele) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = UtilDAO.getConnection();
            statement = connection.prepareStatement(getRequeteFindById(modele));
            statement.setInt(1, modele.getId());
            resultSet = statement.executeQuery();
            getResultById((Patient) modele, resultSet);
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            UtilDAO.closeRessources(resultSet, statement, connection);
        }
    }

    private void getResultById(Patient patient, ResultSet resultSet) throws Exception {
        if (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String nom = resultSet.getNString(2),
                    prenom = resultSet.getString(3),
                    sexe = resultSet.getString(5);
            Date dateNaissance = resultSet.getDate(4);
            patient = new Patient();
            patient.setId(id);
            patient.setNom(nom);
            patient.setPrenom(prenom);
            patient.setSexe(sexe);
            patient.setDateNaissance(dateNaissance);
        }
    }

    @Override
    public void delete(BaseModele modele) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = UtilDAO.getConnection();
            Patient patient = (Patient) modele;
            statement = connection.prepareStatement(getRequeteDelete(patient));
            statement.setInt(1, modele.getId());
            statement.executeUpdate();
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            UtilDAO.closeRessources(null, statement, connection);
        }
    }

    @Override
    public void update(BaseModele modele) throws Exception {
    }

    @Override
    public void save(BaseModele modele) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = UtilDAO.getConnection();
            Patient patient = (Patient) modele;
            statement = connection.prepareStatement(getRequeteSave(patient));
            statement.setString(1, patient.getNom());
            statement.setString(2, patient.getPrenom());
            statement.setDate(3, patient.getDateNaissance());
            statement.setString(4, patient.getSexe());
            statement.executeUpdate();
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            UtilDAO.closeRessources(null, statement, connection);
        }
    }

    @Override
    public String getRequeteFindAll() {
        return "SELECT * FROM Patient";
    }

    @Override
    public String getRequeteFindAll(BaseModele modele) {
        return null;
    }

    @Override
    public String getRequeteFindById(BaseModele modele) {
        String sql = "SELECT * FROM %s WHERE id = ?";
        return String.format(sql, modele.getTable());
    }

    @Override
    public String getRequeteDelete(BaseModele modele) {
        String sql = "DELETE FROM %s WHERE id = ?";
        return String.format(sql, modele.getTable());
    }

    private String getRequeteDelete(Patient patient) {
        String sql = "DELETE FROM %s WHERE id = ?";
        sql = String.format(sql, patient.getTable());
        return sql;
    }

    private String getRequeteSave(Patient patient) {
        String sql = "INSERT INTO %s VALUES(?, ?, ?, ?)";
        sql = String.format(sql, patient.getTable());
        return sql;
    }
}
