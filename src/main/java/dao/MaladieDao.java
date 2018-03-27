package dao;

import modele.BaseModele;
import modele.Maladie;
import utils.Configuration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MaladieDao implements InterfaceDao {
    private final String table = "Maladie";

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
        ArrayList<BaseModele> maladies = new ArrayList<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt(1),
                    idDepartement = resultSet.getInt(4);
            String nom = resultSet.getString(2),
                    description = resultSet.getString(3);
            maladies.add(new Maladie(id, nom, description, idDepartement));
        }
        return maladies;
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
        Maladie maladie = null;
        if (resultSet.next()) {
            Integer id = resultSet.getInt(1),
                    idDepartement = resultSet.getInt(4);
            String nom = resultSet.getString(2),
                    description = resultSet.getString(3);
            maladie = new Maladie(id, nom, description, idDepartement);
        }
        return maladie;
    }

    @Override
    public void delete(BaseModele modele) throws Exception {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(getRequeteDelete(modele));
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            closeRessources(null, statement, connection);
        }
    }

    @Override
    public void update(BaseModele modele) throws Exception {Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            Maladie maladie = (Maladie) modele;
            statement.executeUpdate(getRequeteUpdate(maladie));
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            closeRessources(null, statement, connection);
        }
    }

    private String getRequeteUpdate(Maladie maladie) {
        String sql = "UPDATE %s SET nom = '%s', description = '%s', idDepartement = %d WHERE id = %d";
        return String.format(sql, table, maladie.getNom(), maladie.getDescription(), maladie.getIdDepartement(), maladie.getId());
    }

    @Override
    public void save(BaseModele modele) throws Exception {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            Maladie maladie = (Maladie) modele;
            statement.executeUpdate(getRequeteSave(maladie));
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
    public String getRequeteDelete(BaseModele modele) {
        String sql = "DELETE FROM %s WHERE id = %d";
        return String.format(sql, table, modele.getId());
    }

    private String getRequeteSave(Maladie maladie) {
        String sql = "INSERT INTO %s VALUES('%s', '%s', %d)";
        return String.format(sql, table, maladie.getNom(), maladie.getDescription(), maladie.getIdDepartement());
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
    public Connection getConnection() throws Exception {
        return getConnection(Configuration.jdbc, Configuration.hostname, Configuration.port, Configuration.dbname, Configuration.user, Configuration.pass);
    }

    @Override
    public Connection getConnection(String jdbc, String hostname, int port, String dbname, String user, String pass) throws Exception {
        DBConnection dbConnection = new DBConnection(jdbc, hostname, port, dbname, user, pass);
        return dbConnection.getConnection();
    }
}
