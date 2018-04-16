package dao;

import modele.BaseModele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    void closeRessources(ResultSet resultSet, PreparedStatement statement, Connection connection) throws Exception;
}
