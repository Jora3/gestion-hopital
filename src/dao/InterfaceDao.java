package dao;

import modele.BaseModele;

import java.util.List;

public interface InterfaceDao {
    List<BaseModele> findAll() throws Exception;
    BaseModele findById(BaseModele modele) throws Exception;
    void delete(BaseModele modele) throws Exception;
    void update(BaseModele modele) throws Exception;
    void save(BaseModele modele) throws Exception;
}
