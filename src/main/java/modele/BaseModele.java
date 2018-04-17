package modele;

public class BaseModele {
    private Integer id;
    private String table;

    public BaseModele(Integer id, String table) throws Exception {
        this.setId(id);
        this.setTable(table);
    }

    public BaseModele(String table) throws Exception {
        this.setTable(table);
    }

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

    public String getTable() {
        return table;
    }

    private void setTable(String table) throws Exception {
        if (table.equals(""))
            throw new Exception("Nom de table vide");
        this.table = table;
    }
}
