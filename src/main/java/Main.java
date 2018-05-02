import dao.GenericDAO;
import modele.BaseModele;
import modele.Consultation;
import modele.Medecin;
import modele.Patient;
import java.sql.Date;
import java.util.List;

public class Main {
    @SuppressWarnings(value = {"deprecated"})
    public static void main(String[] args) {
        try {
            Consultation consultation = new Consultation(new Date(118, 5, 2), 1, 1);
            Consultation consultation1 = new Consultation(new Date(118, 5, 3), 1, 2);
            Consultation consultation2 = new Consultation(new Date(118, 5, 4), 1, 3);
            GenericDAO dao = new GenericDAO();
            dao.save(consultation);
            dao.save(consultation1);
            dao.save(consultation2);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
