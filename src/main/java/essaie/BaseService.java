package essaie;

import dao.ChambreDAO;
import dao.GenericDAO;
import dao.MouvementDAO;
import modele.BaseModele;

import java.util.List;
import modele.Chambre;
import modele.Mouvement;

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
    public List<Mouvement> findAllMouvement() throws Exception
    {
        return MouvementDAO.findAll();
    }
     public List<Chambre> findAllChambre() throws Exception
    {
        return ChambreDAO.findAll();
    }
     public void saveMouvement(Mouvement m) throws Exception
     {
         MouvementDAO.save(m);
     }
     public void saveChambre(Chambre m) throws Exception
     {
         ChambreDAO.save(m);
     }
     public void updateMouvement(Mouvement m) throws Exception
     {
         MouvementDAO mov = new MouvementDAO();
         mov.update(m);
     }
     public void updateChambre(Chambre m) throws Exception
     {
         ChambreDAO c = new ChambreDAO();
         c.update(m);
     }
     public void deleteMouvement(int id) throws Exception
     {
         MouvementDAO mov = new MouvementDAO();
         mov.delete(id);
     }
     public void deleteChambre(int id) throws Exception
     {
         ChambreDAO c = new ChambreDAO();
         c.delete(id);
     }
}
