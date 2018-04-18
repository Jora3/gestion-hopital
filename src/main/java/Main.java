import dao.GenericDAO;
import modele.Patient;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        try {
            Patient patient = new Patient("Patient", "nomPatient1", "prenomPatient1", new Date(100, 3, 1), "F");
            (new GenericDAO()).save(patient);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
