/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author Nathalie Andrandrain
 */
import annotations.Column;
import annotations.Table;

@Table(name = "chambre")
public class Chambre extends BaseModele{   
    private String numero;
    private int disponible;

    public Chambre() {
    }
     public Chambre(String numero, int disponible, Integer id) throws Exception {
        super(id);
        this.numero = numero;
        this.disponible = disponible;
    }
    public Chambre(String numero, int disponible) {
        this.numero = numero;
        this.disponible = disponible;
    }
    
    public String getNumero() {
        return numero;
    }


    public int getDisponible() {
        return disponible;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }
    
}
