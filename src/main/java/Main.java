import dao.GenericDAO;
import dao.MouvementDAO;
import modele.Patient;
import java.sql.Date;
import java.util.List;
import modele.Mouvement;

public class Main {
    public static void main(String[] args) {
        try {
            Patient patient = new Patient("Patient", "nomPatient1", "prenomPatient1", new Date(100, 3, 1), "F");
            //(new GenericDAO()).save(patient);
            MouvementDAO mov = new MouvementDAO();
            List<Mouvement> lm = mov.findAll();
            for(int i = 0; i<lm.size(); i++)
            {
                System.out.println(lm.get(i).getChambre());
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
