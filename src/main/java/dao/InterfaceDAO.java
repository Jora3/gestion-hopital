package dao;

import modele.BaseModele;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface InterfaceDAO {
    List<BaseModele> findAll() throws Exception;

    List<BaseModele> findAll(BaseModele baseModele) throws Exception;

    BaseModele findById(BaseModele modele) throws Exception;
    void delete(BaseModele modele) throws Exception;
    void update(BaseModele modele) throws Exception;
    void save(BaseModele modele) throws Exception;
    String getRequeteFindAll();

    String getRequeteFindAll(BaseModele modele);

    String getRequeteFindById(BaseModele modele);
    String getRequeteDelete(BaseModele modele);
    void closeRessources(ResultSet resultSet, Statement statement, Connection connection) throws Exception;
    Connection getConnection() throws Exception;
    Connection getConnection(String jdbc, String hostname, int port, String dbname, String user, String pass) throws Exception;
}
