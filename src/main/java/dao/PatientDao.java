package dao;

import modele.BaseModele;
import modele.Patient;
import utils.Configuration;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PatientDao implements InterfaceDao {
    private final String table = "Patient";

    @Override
    public List<BaseModele> findAll() throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(getRequeteFindAll());
            return getResultAll(resultSet);
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            closeRessources(resultSet, statement, connection);
        }
    }

    private List<BaseModele> getResultAll(ResultSet resultSet) throws Exception {
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
    public BaseModele findById(BaseModele modele) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(getRequeteFindById(modele));
            return getResultById(resultSet);
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            closeRessources(resultSet, statement, connection);
        }
    }

    private BaseModele getResultById(ResultSet resultSet) throws Exception {
        Patient patient = null;
        if (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            String nom = resultSet.getNString(2),
                    prenom = resultSet.getString(3),
                    sexe = resultSet.getString(5);
            Date dateNaissance = resultSet.getDate(4);
            patient = new Patient(id, nom, prenom, dateNaissance, sexe);
        }
        return patient;
    }

    @Override
    public void delete(BaseModele modele) throws Exception {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            Patient patient = (Patient) modele;
            statement.executeUpdate(getRequeteDelete(patient));
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            closeRessources(null, statement, connection);
        }
    }

    @Override
    public void update(BaseModele modele) throws Exception {
    }

    @Override
    public void save(BaseModele modele) throws Exception {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            Patient patient = (Patient) modele;
            statement.executeUpdate(getRequeteSave(patient));
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            closeRessources(null, statement, connection);
        }
    }

    @Override
    public String getRequeteFindAll() {
        String sql = "SELECT * FROM %s";
        return String.format(sql, table);
    }

    @Override
    public String getRequeteFindById(BaseModele modele) {
        String sql = "SELECT * FROM %s WHERE id = %d";
        return String.format(sql, table, modele.getId());
    }

    @Override
    public void closeRessources(ResultSet resultSet, Statement statement, Connection connection) throws Exception {
        if (resultSet != null)
            resultSet.close();
        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }

    @Override
    public String getRequeteDelete(BaseModele modele) {
        String sql = "DELETE FROM %s WHERE id = %d";
        return String.format(sql, table, modele.getId());
    }

    private String getRequeteDelete(Patient patient) {
        String sql = "DELETE FROM %s WHERE id = %d";
        sql = String.format(sql, table, patient.getId());
        return sql;
    }

    private String getRequeteSave(Patient patient) {
        String sql = "INSERT INTO %s VALUES('%s', '%s', '%s', '%s')";
        sql = String.format(sql, table, patient.getNom(), patient.getPrenom(), new SimpleDateFormat("yyyy-MM-dd").format(patient.getDateNaissance()), patient.getSexe());
        return sql;
    }

    @Override
    public Connection getConnection() throws Exception {
        return getConnection(Configuration.jdbc, Configuration.hostname, Configuration.port, Configuration.dbname, Configuration.user, Configuration.pass);
    }

    @Override
    public Connection getConnection(String jdbc, String hostname, int port, String dbname, String user, String pass) throws Exception {
        DBConnection dbConnection = new DBConnection(jdbc, hostname, port, dbname, user, pass);
        return dbConnection.getConnection();
    }
}
