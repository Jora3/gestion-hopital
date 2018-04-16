package modele;

public class BaseModele {
    private Integer id;
    private Integer etat;

    public BaseModele(Integer id) throws Exception {
        this.setId(id);
    }

    public BaseModele() {}

    public Integer getId(){
        return id;
    }

    public void setId(Integer id) throws Exception {
        this.id = id;
    }

    public void setEtat(Integer value){
        this.etat = value;
    }

    public Integer getEtat(){
        return this.etat;
    }
}
