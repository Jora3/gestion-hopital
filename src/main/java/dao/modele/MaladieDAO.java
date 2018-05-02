package dao.modele;

import dao.InterfaceDAO;
import modele.BaseModele;
import modele.Maladie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            UtilDAO.closeRessources(resultSet, statement, connection);
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
    public List<BaseModele> findAll(BaseModele modele, boolean strict) throws Exception {
        return null;
    }

    @Override
    public List<BaseModele> findAll(BaseModele modele, boolean strict, int page, int nbDonne) throws Exception {
        return null;
    }

    @Override
    public void findById(BaseModele modele) throws Exception {
        ResultSet resultSet = null;
        try (
                Connection connection = UtilDAO.getConnection();
                PreparedStatement statement = connection.prepareStatement(getRequeteFindById(modele))
        ) {
            statement.setInt(1, modele.getId());
            resultSet = statement.executeQuery();
            getResultById((Maladie) modele, resultSet);
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
        finally {
            UtilDAO.closeRessources(resultSet, null, null);
        }
    }

    private void getResultById(Maladie maladie, ResultSet resultSet) throws Exception {
        if (resultSet.next()) {
            int id = resultSet.getInt(1),
                    idDepartement = resultSet.getInt(4);
            String nom = resultSet.getString(2),
                    description = resultSet.getString(3);
            maladie = new Maladie(id, nom, description, idDepartement);
            maladie.setId(id);
            maladie.setNom(nom);
            maladie.setDescription(description);
            maladie.setIdDepartement(idDepartement);
        }
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
            UtilDAO.closeRessources(null, statement, connection);
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
}
