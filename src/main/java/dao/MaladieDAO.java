package dao;

import modele.BaseModele;
import modele.Maladie;
import utils.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MaladieDAO implements InterfaceDAO {

    @Override
    public List<BaseModele> findAll() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = UtilDAO.getConnection();
            statement = connection.prepareStatement(getRequeteFindAll());
            resultSet = statement.executeQuery();
            return getResultAllMaladies(resultSet);
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            closeRessources(resultSet, statement, connection);
        }
    }

    private List<BaseModele> getResultAllMaladies(ResultSet resultSet) throws Exception {
        ArrayList<BaseModele> maladies = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1),
                    idDepartement = resultSet.getInt(4);
            String nom = resultSet.getString(2),
                    description = resultSet.getString(3);
            maladies.add(new Maladie(id, nom, description, idDepartement));
        }
        return maladies;
    }

    @Override
    public List<BaseModele> findAll(BaseModele baseModele) throws Exception {
        return null;
    }

    @Override
    public BaseModele findById(BaseModele modele) throws Exception {
        ResultSet resultSet = null;
        try (
                Connection connection = UtilDAO.getConnection();
                PreparedStatement statement = connection.prepareStatement(getRequeteFindById(modele))
        ) {
            statement.setInt(1, modele.getId());
            resultSet = statement.executeQuery();
            return  getResultById(resultSet);
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            closeRessources(resultSet, null, null);
        }
    }

    private BaseModele getResultById(ResultSet resultSet) throws Exception {
        Maladie maladie = null;
        if (resultSet.next()) {
            int id = resultSet.getInt(1),
                    idDepartement = resultSet.getInt(4);
            String nom = resultSet.getString(2),
                    description = resultSet.getString(3);
            maladie = new Maladie(id, nom, description, idDepartement);
        }
        return maladie;
    }

    @Override
    public void delete(BaseModele modele) throws Exception {

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
            statement = connection.prepareStatement(getRequeteSave(modele));
            Maladie maladie = (Maladie) modele;
            statement.setString(1, maladie.getNom());
            statement.setString(2, maladie.getDescription());
            statement.setInt(3, maladie.getIdDepartement());
            statement.executeUpdate();
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            closeRessources(null, statement, connection);
        }
    }

    private String getRequeteSave(BaseModele modele) {
        String sql = "INSERT INTO %s (nom, description, idDepartement) VALUES (?, ?, ?)";
        return String.format(sql, modele.getTable());
    }

    @Override
    public String getRequeteFindAll() {
        return  "SELECT * FROM Maladie";
    }

    @Override
    public String getRequeteFindAll(BaseModele modele) {
        String sql = "SELECT * FROM %s";
        return String.format(sql, modele.getTable());
    }

    @Override
    public String getRequeteFindById(BaseModele modele) {
        String sql = "SELECT * FROM %s WHERE idMaladie = ?";
        return String.format(sql, modele.getTable());
    }

    @Override
    public String getRequeteDelete(BaseModele modele) {
        return null;
    }

    @Override
    public void closeRessources(ResultSet resultSet, PreparedStatement statement, Connection connection) throws Exception {
        if (resultSet != null)
            resultSet.close();
        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }
}
