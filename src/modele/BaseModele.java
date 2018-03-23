package modele;

public class BaseModele {
    private Integer id;

    public BaseModele(Integer id) throws Exception {
        this.setId(id);
    }

    public BaseModele() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) throws Exception {
        if (id > 0)
            this.id = id;
        else
            throw new Exception("Id négatif : " + id);
    }
}
