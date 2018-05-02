import essaie.BaseService;
import modele.Mouvement;

public class Main {
    public static void main(String[] args) {
        try {
            BaseService service = new BaseService();
            service.save(new Mouvement());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
