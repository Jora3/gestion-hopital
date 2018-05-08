import dao.GenericDAO;
import dao.MouvementDAO;
import modele.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<BaseModele> modeles = (new GenericDAO()).findAll(new Medecin());
            for (BaseModele modele : modeles) {
                Medecin medecin = (Medecin)modele;
                System.out.println("Medecin");
                System.out.println("nom : " + medecin.getNom());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
