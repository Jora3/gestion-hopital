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
import annotations.NotColumn;
import annotations.Table;

@Table(name = "chambre")
public class Chambre extends BaseModele{ 
    @Column(name = "numero")
    private String numeroChambre;
    private int disponible;
    @NotColumn private String hotel;
    
    public Chambre() {
    }
     public Chambre(String numero, int disponible, Integer id) throws Exception {
        super(id);
        this.numeroChambre = numero;
        this.disponible = disponible;
    }
    public Chambre(String numero, int disponible) {
        this.numeroChambre = numero;
        this.disponible = disponible;
    }
    
    public String getNumero() {
        return numeroChambre;
    }


    public int getDisponible() {
        return disponible;
    }

    public void setNumero(String numero) {
        this.numeroChambre = numero;
    }
    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }
    
}
