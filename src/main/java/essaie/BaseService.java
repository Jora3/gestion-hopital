package essaie;

import dao.GenericDAO;
import modele.BaseModele;

import java.util.List;

public class BaseService {
    private final GenericDAO genericDAO = new GenericDAO();

    public void save(BaseModele modele){
        try {
            genericDAO.save(modele);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BaseModele> findAll(BaseModele modele) throws Exception {
        return genericDAO.findAll(modele);
    }

    public BaseModele findById(BaseModele modele) throws Exception {
        return null;//genericDAO.findById(modele);
    }
}
