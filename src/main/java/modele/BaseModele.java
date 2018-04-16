package modele;

public class BaseModele {
    private Integer id;
    private Integer etat;
    private String table;

    public BaseModele(Integer id) throws Exception {
        this.setId(id);
    }

    public BaseModele() {}

    public Integer getId(){
        return id;
    }

    public void setId(Integer id) throws Exception {
        if (id <= 0)
            throw new Exception("Id négatif ou nul : " + id);
        this.id = id;
    }

    public void setEtat(Integer value){
        this.etat = value;
    }

    public Integer getEtat(){
        return this.etat;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) throws Exception {
        if (table.equals(""))
            throw new Exception("Nom de table vide");
        this.table = table;
    }
}
