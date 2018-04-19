import dao.GenericDAO;
import modele.BaseModele;
import modele.Patient;
import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Patient patient = new Patient("Patient", "nomPatient1", "prenomPatient1", new Date(100, 3, 1), "F");
            List<BaseModele> liste = (new GenericDAO()).findAll(new Ecographie());
            for (BaseModele m:liste) {
                Ecographie ecographie = (Ecographie)m;
                System.out.println(ecographie.getType_eco());
            }

        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
