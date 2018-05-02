package dao;

import modele.BaseModele;
import java.util.List;

public interface InterfaceDAO {

    List<BaseModele> findAll(BaseModele modele, boolean strict, int page, int nbDonne) throws Exception;

    List<BaseModele> findAll(BaseModele modele, boolean strict) throws Exception;

    /**
     * select * from Table where column1 = value1 and ... column = valueN;
     * default find strict = true; condition = and;
     * @Object GenericDAO
     * */
    List<BaseModele> findAll(BaseModele baseModele) throws Exception;

    List<BaseModele> findAll() throws Exception;

    void findById(BaseModele modele) throws Exception;

    void delete(BaseModele modele) throws Exception;

    void update(BaseModele modele) throws Exception;

    void save(BaseModele modele) throws Exception;

    String getRequeteFindAll();

    String getRequeteFindAll(BaseModele modele) throws Exception;

    String getRequeteFindById(BaseModele modele) throws Exception;

    String getRequeteDelete(BaseModele modele) throws Exception;
}
