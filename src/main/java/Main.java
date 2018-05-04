import dao.GenericDAO;
import dao.MouvementDAO;
import modele.Patient;
import java.sql.Date;
import java.util.List;
import modele.BaseModele;
import modele.Mouvement;

public class Main {
    public static void main(String[] args) {
        try {
            Patient patient = new Patient("Patient", "nomPatient1", "prenomPatient1", new Date(100, 3, 1), "F");
            //(new GenericDAO()).save(patient);
            
            GenericDAO mov = new GenericDAO();
            BaseModele b=new BaseModele("Chambre");
            List<BaseModele> lm = mov.findAll(b);
            for(int i = 0; i<lm.size(); i++)
            {
                System.out.println(lm.get(i).getId());
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
