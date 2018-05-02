package essaie;

import dao.GenericDAO;
import modele.Mouvement;

public class BaseService {
    private final GenericDAO genericDAO = new GenericDAO();

    public void save(Mouvement mouvement) throws Exception {
        genericDAO.save(mouvement);
    }
}
