package dao;

import annotations.Column;
import annotations.NotColumn;
import annotations.Relation;
import annotations.Table;
import modele.BaseModele;
import modele.Medecin;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenericDAO implements InterfaceDAO {

    private String pagination(int nPage, int nDonne) {
        int offSet = (nPage - 1) * nDonne;
        return String.format(" limit %d offset %d", nDonne, offSet);
    }

    private String nomTable(BaseModele modele) throws Exception {
        Table table = modele.getClass().getAnnotation(Table.class);
        if(table == null){
            if(modele.getTable() == null)
                throw new Exception(String.format("Objet de type : %s n'est pas associé à une table", modele.getClass()));
            return modele.getTable();
        }
        return table.name();
    }

    private String where(BaseModele modele, boolean isAnd) throws IllegalAccessException {
        StringBuilder request  = new StringBuilder();
        String        condition = "or ";
        if(isAnd) condition = "and ";

        Field[] fields = columnsTable(modele.getClass().getDeclaredFields());
        for(Field field : fields){
            Column column = field.getAnnotation(Column.class);
            field.setAccessible(true);
            if(field.get(modele) != null)
                if(column != null) request.append(column.name()).append(" = ? ").append(condition);
                else request.append(field.getName()).append(" = ? ").append(condition);
        }
        int taille = request.length();
        if(5 < taille){
            request.delete(taille - 5, taille - 1);
            request = new StringBuilder(" where "+request);
        }
        return request.toString();
    }

    private Field[] columnsTable(Field[] fields) {
        List<Field> listColumns = new ArrayList<>();
        for (Field field : fields) {
            if(field.getAnnotation(NotColumn.class) == null) listColumns.add(field);
        }
        return listColumns.toArray(new Field[0]);
    }

    private String getColumns(BaseModele modele) {
        Field[] fields = columnsTable(modele.getClass().getDeclaredFields());
        StringBuilder columns = new StringBuilder();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null)
                columns.append(column.name()).append(",");
            else
                columns.append(field.getName()).append(",");
        }
        return columns.substring(0, columns.length()-1);
    }

    private String getParams(BaseModele modele) {
        int nbColumns = columnsTable(modele.getClass().getDeclaredFields()).length;
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < nbColumns; i++)
            params.append("?,");
        return params.substring(0, params.length()-1);
    }

    private String getColumnsForSearch(BaseModele modele) {
        Field[] fields = columnsTable(modele.getClass().getDeclaredFields());
        StringBuilder columns = new StringBuilder();
        int taille = fields.length-1;
        for (int i=0; i<taille;i++) {
            Column column = fields[i].getAnnotation(Column.class);
            if (column != null)
                columns.append(column.name()).append(" || '. ' || ");
            else
                columns.append(fields[i].getName()).append(" || '. ' || ");
        }
        if (fields[taille].getAnnotation(Column.class) != null)
            columns.append(fields[taille].getAnnotation(Column.class).name());
        else
            columns.append(fields[taille].getName());
        return columns.substring(0, columns.length());
    }

    private String getRequeteFullText(BaseModele modele) throws IllegalAccessException {
        Table table = modele.getClass().getAnnotation(Table.class);
        String document=getColumnsForSearch(modele);
        Field[] fields = columnsTable(modele.getClass().getDeclaredFields());
        int taille= fields.length-1;
        String text = "";
        for (int i=0; i<taille;i++) {
            fields[i].setAccessible(true);
            Object o = fields[i].get(modele);
            text=text+" "+o+" |";
        }
        fields[taille].setAccessible(true);
        text = text+" "+fields[taille].get(modele);
        return String.format("SELECT * FROM \"%s\" WHERE to_tsvector( %s ) @@ to_tsquery('%s');", table.name(), document, text);
    }

    /**
    * Callable after rs.next()
    * */
    private void setValues(BaseModele modele, ResultSet rs, Field[] fields) throws IllegalAccessException, SQLException {
        for(Field f : fields){
            f.setAccessible(true);
            Column column = f.getAnnotation(Column.class);
            if(column != null){ f.set(modele, rs.getObject(column.name()));}
            else f.set(modele, rs.getObject(f.getName()));
        }
    }

    private void set(BaseModele modele, ResultSet rs) throws Exception {
        try {
            Field[]   fields  = columnsTable(modele.getClass().getDeclaredFields());
            if (rs.next()){
                modele = modele.getClass().newInstance();
                modele.setId(rs.getInt("id"));
                setValues(modele, rs, fields);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private List<BaseModele> list(BaseModele modele, ResultSet rs) throws Exception {
        List<BaseModele> modeleList = new ArrayList<>();
        try {
            if (cacheExist(modele))
                return deserialiserAll(modele);
            Class classes = modele.getClass();
            Field[]   fields  = columnsTable(classes.getDeclaredFields());
            while (rs.next()){
                modele = modele.getClass().newInstance();
                modele.setId(rs.getInt("id"));
                setValues(modele, rs, fields);
                modeleList.add(modele);
            }
            serialiserAll(modele, modeleList);
            return modeleList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private void serialiserAll(BaseModele modele, List<BaseModele> modeles) throws Exception {
        FileOutputStream fichier = null;
        ObjectOutputStream stream = null;
        try {
            String nomFichier = "cache/All" + modele.getClass().getSimpleName() + ".ser";
            fichier = new FileOutputStream(nomFichier);
            stream = new ObjectOutputStream(fichier);
            stream.writeObject(modeles);
            stream.flush();
        } catch (Exception exception) {
            throw exception;
        } finally {
            if (fichier != null)
                fichier.close();
            if (stream != null)
                stream.close();
        }
    }

    private boolean cacheExist(BaseModele modele) {
        String filePath = "cache/All" + modele.getClass().getSimpleName() + ".ser";
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    private List<BaseModele> deserialiserAll(BaseModele modele) throws Exception {
        FileInputStream fichier = null;
        ObjectInputStream stream = null;
        try {
            String nomFichier = "cache/All" + modele.getClass().getSimpleName() + ".ser";
            fichier = new FileInputStream(nomFichier);
            stream = new ObjectInputStream(fichier);
            return (List<BaseModele>) stream.readObject();
        } catch (Exception exception) {
            throw exception;
        } finally {
            if (fichier != null)
                fichier.close();
            if (stream != null)
                stream.close();
        }
    }

    @Override
    public List<BaseModele> findAll(BaseModele modele, boolean strict, int page, int nbDonne) throws Exception {
        ResultSet rs = null;
        String q = getRequeteFindAll(modele), aWhere = where(modele, strict), pagination = pagination(page, nbDonne);
        q += aWhere + pagination;
        System.out.println(q);
        try (Connection conn = UtilDAO.getConnection();
             PreparedStatement statement = conn.prepareStatement(q)) {
            if(!aWhere.equals("")){
                Field[] fields = columnsTable(modele.getClass().getDeclaredFields());
                int     i      = 1;
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object o = field.get(modele);
                    if(o != null){
                        statement.setObject(i, o);
                        i++;
                    }
                }
            }
            return list(modele, rs = statement.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            UtilDAO.closeRessources(rs, null, null);
        }
    }

    public List<BaseModele> findFullText(BaseModele modele) throws Exception {
        ResultSet rs = null;
        String q = getRequeteFullText(modele);
        System.out.println(q);
        try (Connection conn = UtilDAO.getConnection();
             PreparedStatement statement = conn.prepareStatement(q)) {
            return list(modele, rs = statement.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            UtilDAO.closeRessources(rs, null, null);
        }
    }

    @Override
    public List<BaseModele> findAll(BaseModele modele, boolean strict) throws Exception {
        return findAll(modele, strict, 1, 20);
    }

    @Override
    public List<BaseModele> findAll(BaseModele modele) throws Exception {
        return findAll(modele, true, 1, 20);
    }

    @Override
    public List<BaseModele> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public void findById(BaseModele modele) throws Exception {
        ResultSet rs = null;
        try (Connection conn = UtilDAO.getConnection();
             PreparedStatement statement = conn.prepareStatement(getRequeteFindById(modele))) {
            statement.setObject(1, modele.getId());
            rs = statement.executeQuery();
            set(modele, rs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            UtilDAO.closeRessources(rs, null, null);
        }
    }

    private void removeCache(BaseModele modele) {
        String filePath = "cache/All" + modele.getClass().getSimpleName() + ".ser";
        (new File(filePath)).delete();
    }

    @Override
    public void delete(BaseModele modele) throws Exception {
        try(Connection conn = UtilDAO.getConnection();
        PreparedStatement statement= conn.prepareStatement(getRequeteDelete(modele))){
            statement.setObject(1,modele.getId());
            statement.executeUpdate();
            removeCache(modele);
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
     public void update(BaseModele modele) throws Exception {
         try(Connection c = UtilDAO.getConnection();
            PreparedStatement st = c.prepareStatement(getRequeteUpdate(modele))){
            int i = 1;
            Field[]   fields  = columnsTable(modele.getClass().getDeclaredFields());
            for(Field field : fields){
                field.setAccessible(true);
                st.setObject(i, field.get(modele));
                i++;
            }
            st.setObject(i, modele.getId());
            st.executeUpdate();
             removeCache(modele);
        }
    }

    @Override
    public void save(BaseModele modele) throws Exception {
        try (
            Connection connection = UtilDAO.getConnection();
            PreparedStatement statement = connection.prepareStatement(getRequeteSave(modele))
            ) {
            setParamsSave(statement, modele);
            System.out.println(statement);
            statement.executeUpdate();
            removeCache(modele);
        }
        catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    private void setParamsSave(PreparedStatement statement, BaseModele modele) throws Exception {
        Class classe = modele.getClass();
        Field[] fields = columnsTable(classe.getDeclaredFields());
        int i = 1;
        for (Field field : fields) {
            field.setAccessible(true);
            statement.setObject(i, field.get(modele));
            i++;
        }
    }

    private String getRequeteSave(BaseModele modele) throws Exception {
        String sql = "INSERT INTO %s (%s) VALUES(%s)";
        return String.format(sql, nomTable(modele), getColumns(modele), getParams(modele));
    }

    @Override
    public String getRequeteFindAll() {
        return null;
    }

    @Override
    public String getRequeteFindAll(BaseModele modele) throws Exception {
        return String.format("select * from %s", nomTable(modele));
    }

    @Override
    public String getRequeteFindById(BaseModele modele) throws Exception {
        return String.format("select * from %s where id = ?", nomTable(modele));
    }

    private String getRequeteUpdate(BaseModele modele) throws Exception {
        Field[]       fields = columnsTable(modele.getClass().getDeclaredFields());
        StringBuilder values = new StringBuilder();
        for(Field f : fields){
            values.append(",").append(f.getName()).append(" = ?");
        }
          values = new StringBuilder(values.toString().replaceFirst(",", ""));
        return String.format("update %s set %s where id = ?", nomTable(modele), values.toString());
    }

    @Override
    public String getRequeteDelete(BaseModele modele) throws Exception {
        return String.format("delete from %s where id= ?", nomTable(modele));
    }
}
